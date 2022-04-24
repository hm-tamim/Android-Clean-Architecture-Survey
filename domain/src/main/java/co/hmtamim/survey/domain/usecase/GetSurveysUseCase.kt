package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSurveysUseCase @Inject constructor(private val repository: SurveyRepository) {

    suspend fun execute(): UseCaseResult<Flow<List<SurveyModel>>> {
        return try {
            val response = repository.getSurveyList()
            UseCaseResult.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}
