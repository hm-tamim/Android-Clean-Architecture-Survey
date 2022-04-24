package co.hmtamim.survey.data.repositoryimpl

import co.hmtamim.survey.data.constant.GRANT_PASSWORD
import co.hmtamim.survey.data.constant.GRANT_REFRESH_TOKEN
import co.hmtamim.survey.data.mapper.ForgotPassResponseMapper
import co.hmtamim.survey.data.mapper.LoginResponseMapper
import co.hmtamim.survey.data.mapper.mapFromResponseToEntity
import co.hmtamim.survey.data.request.ForgotPasswordRequest
import co.hmtamim.survey.data.request.LoginRequest
import co.hmtamim.survey.data.request.RefreshTokenRequest
import co.hmtamim.survey.data.response.toCredential
import co.hmtamim.survey.data.service.ApiService
import co.hmtamim.survey.data.util.ResponseHandler
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.CredentialModel
import co.hmtamim.survey.domain.repository.AuthRepository
import co.hmtamim.survey.domain.usecase.LoginUseCase
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val loginResponseMapper: LoginResponseMapper,
    private val forgotPasswordResponseMapper: ForgotPassResponseMapper,
    private val responseHandler: ResponseHandler,
) : AuthRepository {

    override suspend fun login(body: LoginUseCase.Param): ApiResponse<CredentialModel> {
        return mapFromResponseToEntity(responseHandler.performApiCall {
            apiService.login(
                LoginRequest(
                    grantType = GRANT_PASSWORD,
                    email = body.email,
                    password = body.password
                )
            )
        }, loginResponseMapper)
    }

    override suspend fun forgotPassword(email: String): ApiResponse<String> {
        return mapFromResponseToEntity(responseHandler.performApiCall {
            apiService.forgotPassword(
                ForgotPasswordRequest(
                    user = ForgotPasswordRequest.User(
                        email = email
                    )
                )
            )
        }, forgotPasswordResponseMapper)
    }

    override suspend fun refreshToken(refreshToken: String): CredentialModel {
        return apiService.refreshToken(
            RefreshTokenRequest(
                grantType = GRANT_REFRESH_TOKEN,
                refreshToken = refreshToken
            )
        ).toCredential()
    }

}
