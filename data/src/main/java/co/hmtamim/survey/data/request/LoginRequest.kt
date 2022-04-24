package co.hmtamim.survey.data.request

import com.squareup.moshi.Json

data class LoginRequest(
    @Json(name = "grant_type") val grantType: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
) : BaseRequest()