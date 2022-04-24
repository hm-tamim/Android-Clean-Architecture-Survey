package co.hmtamim.survey.data.response

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "errors") val errors: List<ErrorsItem?>? = null
)

data class ErrorsItem(
    @Json(name = "code") val code: String? = null,
    @Json(name = "source") val source: Source? = null,
    @Json(name = "detail") val detail: String? = null
)

data class Source(
    @Json(name = "parameter") val parameter: String? = null
)



