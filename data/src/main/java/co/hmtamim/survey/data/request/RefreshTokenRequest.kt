package co.hmtamim.survey.data.request

import com.squareup.moshi.Json

data class RefreshTokenRequest(
    @Json(name = "grant_type") val grantType: String,
    @Json(name = "refresh_token") val refreshToken: String,
) : BaseRequest()