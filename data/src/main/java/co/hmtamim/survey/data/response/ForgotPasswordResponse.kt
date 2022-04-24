package co.hmtamim.survey.data.response

import com.squareup.moshi.Json

data class ForgotPasswordResponse(
    @Json(name = "meta") val meta: Meta
) {

    data class Meta(
        @Json(name = "message") val message: String
    )
}


