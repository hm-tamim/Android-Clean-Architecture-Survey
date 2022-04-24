package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.repository.SurveyRepository
import javax.inject.Inject

class FetchSurveysUseCase @Inject constructor(private val repository: SurveyRepository) {

    suspend fun execute(): ApiResponse<List<SurveyModel>> {
        return repository.fetchSurveyList()
    }
}
