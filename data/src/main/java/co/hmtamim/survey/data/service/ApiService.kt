package co.hmtamim.survey.data.service

import co.hmtamim.survey.data.request.ForgotPasswordRequest
import co.hmtamim.survey.data.request.LoginRequest
import co.hmtamim.survey.data.request.RefreshTokenRequest
import co.hmtamim.survey.data.response.ForgotPasswordResponse
import co.hmtamim.survey.data.response.LoginResponse
import co.hmtamim.survey.data.response.SurveyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/oauth/token")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST("api/v1/passwords")
    suspend fun forgotPassword(@Body body: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @GET("api/v1/surveys")
    suspend fun getSurveyList(): Response<SurveyResponse>

    @POST("api/v1/oauth/token")
    suspend fun refreshToken(@Body body: RefreshTokenRequest): LoginResponse

}
