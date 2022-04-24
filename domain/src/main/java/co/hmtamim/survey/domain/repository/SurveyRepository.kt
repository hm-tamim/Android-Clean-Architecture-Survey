package co.hmtamim.survey.domain.repository

import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.SurveyModel
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun fetchSurveyList(): ApiResponse<List<SurveyModel>>
    suspend fun getSurveyList(): Flow<List<SurveyModel>>
    suspend fun insertSurveys(entities: List<SurveyModel>)
    suspend fun deleteOldSurveys(surveyIds: List<String>)
    suspend fun deleteAll()
}
