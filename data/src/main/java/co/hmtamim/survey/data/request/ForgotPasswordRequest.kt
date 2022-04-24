package co.hmtamim.survey.data.request

import com.squareup.moshi.Json

data class ForgotPasswordRequest(
    @Json(name = "user") val user: User,
) : BaseRequest() {

    data class User(
        @Json(name = "email") val email: String,
    )

}