package co.hmtamim.survey.data.mapper

import co.hmtamim.survey.data.response.ForgotPasswordResponse
import javax.inject.Inject

class ForgotPassResponseMapper @Inject constructor() :
    Mapper<ForgotPasswordResponse, String> {
    override fun mapFromApiResponse(source: ForgotPasswordResponse): String {
        return source.meta.message
    }
}