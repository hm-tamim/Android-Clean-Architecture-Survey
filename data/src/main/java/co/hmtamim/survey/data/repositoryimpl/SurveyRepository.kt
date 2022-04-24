package co.hmtamim.survey.data.repositoryimpl

import co.hmtamim.survey.data.database.dao.SurveysDao
import co.hmtamim.survey.data.mapper.SurveyResponseMapper
import co.hmtamim.survey.data.mapper.mapFromResponseToEntity
import co.hmtamim.survey.data.service.ApiService
import co.hmtamim.survey.data.util.ResponseHandler
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val surveysDao: SurveysDao,
    private val surveyResponseMapper: SurveyResponseMapper,
    private val responseHandler: ResponseHandler,
) : SurveyRepository {

    override suspend fun fetchSurveyList(): ApiResponse<List<SurveyModel>> {
        return mapFromResponseToEntity(responseHandler.performApiCall {
            apiService.getSurveyList()
        }, surveyResponseMapper)
    }

    override suspend fun getSurveyList(): Flow<List<SurveyModel>> {
        return surveysDao.getAllList()
    }

    override suspend fun insertSurveys(entities: List<SurveyModel>) {
        return surveysDao.insertAll(entities)
    }

    override suspend fun deleteOldSurveys(surveyIds: List<String>) {
        return surveysDao.deleteOld(surveyIds)
    }

    override suspend fun deleteAll() {
        return surveysDao.deleteAll()
    }

}
