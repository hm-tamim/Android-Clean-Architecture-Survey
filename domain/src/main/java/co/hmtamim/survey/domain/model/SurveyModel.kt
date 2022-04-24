package co.hmtamim.survey.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "survey_list")
data class SurveyModel(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
) : Serializable

fun List<SurveyModel>.toIds(): List<String> {
    return this.map { it.id }
}

