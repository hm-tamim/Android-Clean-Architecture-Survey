package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.repository.SurveyRepository
import javax.inject.Inject

class InsertSurveysUseCase @Inject constructor(private val repository: SurveyRepository) {

    suspend fun execute(surveys: List<SurveyModel>): UseCaseResult<Unit> {
        return try {
            val inserted = repository.insertSurveys(surveys)
            UseCaseResult.Success(inserted)
        } catch (e: Exception) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}
