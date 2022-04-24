package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.repository.SurveyRepository
import javax.inject.Inject

class DeleteOldSurveysUseCase @Inject constructor(private val repository: SurveyRepository) {

    suspend fun execute(surveyIds: List<String>): UseCaseResult<Unit> {
        return try {
            val inserted = repository.deleteOldSurveys(surveyIds)
            UseCaseResult.Success(inserted)
        } catch (e: Exception) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}
