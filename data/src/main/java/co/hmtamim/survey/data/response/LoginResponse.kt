package co.hmtamim.survey.data.response

import co.hmtamim.survey.domain.model.CredentialModel
import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "data") val data: Data? = null,
) {
    data class Data(
        @Json(name = "attributes") val attributes: Attributes? = null,
        @Json(name = "id") val id: String? = null,
        @Json(name = "type") val type: String? = null
    ) {
        data class Attributes(
            @Json(name = "access_token") val accessToken: String? = null,
            @Json(name = "refresh_token") val refreshToken: String? = null,
            @Json(name = "created_at") val createdAt: Int? = null,
            @Json(name = "token_type") val tokenType: String? = null,
            @Json(name = "expires_in") val expiresIn: Int? = null
        )
    }
}

fun LoginResponse.toCredential(): CredentialModel {
    return CredentialModel(
        accessToken = this.data?.attributes?.accessToken,
        refreshToken = this.data?.attributes?.refreshToken,
    )
}





