package co.hmtamim.survey.domain.usecases.repository

import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSurveyRepository : SurveyRepository {

    private val surveys = mutableListOf<SurveyModel>()

    override suspend fun fetchSurveyList(): ApiResponse<List<SurveyModel>> {
        return ApiResponse.success(surveys)
    }

    override suspend fun getSurveyList(): Flow<List<SurveyModel>> {
        return flow { emit(surveys) }
    }

    override suspend fun insertSurveys(entities: List<SurveyModel>) {
        surveys.addAll(entities)
    }

    override suspend fun deleteOldSurveys(surveyIds: List<String>) {
        surveys.removeAll {
            !surveyIds.contains(it.id)
        }
    }

    override suspend fun deleteAll() {
        surveys.clear()
    }
}