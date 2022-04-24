package co.hmtamim.survey.data.service

import co.hmtamim.survey.data.constant.AUTHORIZATION_HEADER
import co.hmtamim.survey.data.constant.GRANT_REFRESH_TOKEN
import co.hmtamim.survey.data.constant.PREFIX_BEARER
import co.hmtamim.survey.data.observer.GlobalObserver
import co.hmtamim.survey.data.request.RefreshTokenRequest
import co.hmtamim.survey.data.response.toCredential
import co.hmtamim.survey.domain.usecase.TokenUseCases
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthenticationRefreshToken @Inject constructor(
    private var tokenUseCases: TokenUseCases,
    private var refreshTokenService: RefreshTokenService,
    private var globalObserver: GlobalObserver
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code == 401) { // if token expired
            runBlocking {
                with(tokenUseCases) {
                    getRefreshTokenUseCase.execute()?.let {
                        try {
                            val credentialModel = refreshTokenService.refreshToken(
                                RefreshTokenRequest(
                                    grantType = GRANT_REFRESH_TOKEN,
                                    refreshToken = it
                                )
                            ).toCredential()

                            credentialModel.let { model ->
                                model.refreshToken?.let { token ->
                                    saveRefreshTokenUseCase.execute(
                                        token
                                    )
                                }
                                model.accessToken?.let { token ->
                                    saveAccessTokenUseCase.execute(token)
                                    response.request
                                        .newBuilder()
                                        .header(AUTHORIZATION_HEADER, "$PREFIX_BEARER $token")
                                        .build()
                                }
                            } ?: run {
                                null
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            when (e) {
                                is retrofit2.HttpException -> {
                                    if (e.code() == 400 || e.code() == 401) {
                                        globalObserver.onRefreshTokenExpiredLogout.send(true)
                                    }
                                }
                            }
                            null
                        }
                    }
                }
            }
        } else {
            null
        }
    }

}