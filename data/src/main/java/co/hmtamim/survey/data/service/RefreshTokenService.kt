package co.hmtamim.survey.data.service

import co.hmtamim.survey.data.request.RefreshTokenRequest
import co.hmtamim.survey.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {

    @POST("api/v1/oauth/token")
    suspend fun refreshToken(@Body body: RefreshTokenRequest): LoginResponse

}
