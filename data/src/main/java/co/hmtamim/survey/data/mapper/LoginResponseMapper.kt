package co.hmtamim.survey.data.mapper

import co.hmtamim.survey.data.response.LoginResponse
import co.hmtamim.survey.domain.model.CredentialModel
import javax.inject.Inject

class LoginResponseMapper @Inject constructor() :
    Mapper<LoginResponse, CredentialModel> {
    override fun mapFromApiResponse(source: LoginResponse): CredentialModel {
        return CredentialModel(
            accessToken = source.data?.attributes?.accessToken,
            refreshToken = source.data?.attributes?.refreshToken
        )
    }
}