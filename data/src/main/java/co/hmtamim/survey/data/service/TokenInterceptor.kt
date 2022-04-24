package co.hmtamim.survey.data.service

import co.hmtamim.survey.data.constant.AUTHORIZATION_HEADER
import co.hmtamim.survey.data.constant.PREFIX_BEARER
import co.hmtamim.survey.domain.usecase.TokenUseCases
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class TokenInterceptor constructor(
    private var tokenUseCases: TokenUseCases
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        tokenUseCases.getAccessTokenUseCase.execute()?.let {
            val newRequest: Request = originalRequest.newBuilder()
                .addHeader(AUTHORIZATION_HEADER, "$PREFIX_BEARER $it")
                .build()
            return chain.proceed(newRequest)
        }

        return chain.proceed(originalRequest)
    }
}