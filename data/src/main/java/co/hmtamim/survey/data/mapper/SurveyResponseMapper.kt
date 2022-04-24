package co.hmtamim.survey.data.mapper

import co.hmtamim.survey.data.response.SurveyResponse
import co.hmtamim.survey.data.response.toSurveys
import co.hmtamim.survey.domain.model.SurveyModel
import javax.inject.Inject

class SurveyResponseMapper @Inject constructor() :
    Mapper<SurveyResponse, List<SurveyModel>> {
    override fun mapFromApiResponse(source: SurveyResponse): List<SurveyModel> {
        return source.data.toSurveys()
    }
}