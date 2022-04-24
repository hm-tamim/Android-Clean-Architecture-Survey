package co.hmtamim.survey.data.response

import co.hmtamim.survey.domain.model.SurveyModel
import com.squareup.moshi.Json

data class SurveyResponse(
    @Json(name = "data") val data: List<SurveyDataItem>
) {
    data class SurveyDataItem(
        @Json(name = "relationships")
        val relationships: Relationships? = null,
        @Json(name = "attributes") val attributes: Attributes,
        @Json(name = "id") val id: String,
        @Json(name = "type") val type: String? = null
    ) {
        data class Attributes(
            @Json(name = "is_active") val isActive: Boolean? = null,
            @Json(name = "thank_email_above_threshold") val thankEmailAboveThreshold: String? = null,
            @Json(name = "cover_image_url") val coverImageUrl: String,
            @Json(name = "description") val description: String,
            @Json(name = "created_at") val createdAt: String? = null,
            @Json(name = "inactive_at") val inactiveAt: Any? = null,
            @Json(name = "title") val title: String,
            @Json(name = "active_at") val activeAt: String? = null,
            @Json(name = "thank_email_below_threshold") val thankEmailBelowThreshold: String? = null,
            @Json(name = "survey_type") val surveyType: String? = null
        )

        data class Relationships(
            @Json(name = "questions") val questions: Questions? = null
        ) {
            data class Questions(
                @Json(name = "data") val data: List<DataItem?>? = null
            ) {
                data class DataItem(
                    val id: String,
                    val type: String
                )
            }
        }
    }
}

fun SurveyResponse.SurveyDataItem.toSurveyUiModel(): SurveyModel {
    return SurveyModel(
        id = id,
        title = attributes.title,
        description = attributes.description,
        coverImageUrl = attributes.coverImageUrl + "l" // append l to get hi-res image
    )
}

fun List<SurveyResponse.SurveyDataItem>.toSurveys(): List<SurveyModel> {
    return this.map { it.toSurveyUiModel() }
}








