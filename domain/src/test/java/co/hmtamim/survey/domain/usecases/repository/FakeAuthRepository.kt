package co.hmtamim.survey.domain.usecases.repository

import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.CredentialModel
import co.hmtamim.survey.domain.repository.AuthRepository
import co.hmtamim.survey.domain.usecase.LoginUseCase

class FakeAuthRepository : AuthRepository {

    override suspend fun login(body: LoginUseCase.Param): ApiResponse<CredentialModel> {
        return ApiResponse.success(
            CredentialModel(
                accessToken = "aaa",
                refreshToken = "bbb"
            )
        )
    }

    override suspend fun forgotPassword(email: String): ApiResponse<String> {
        return ApiResponse.success("Reset link sent to email")
    }

    override suspend fun refreshToken(refreshToken: String): CredentialModel {
        return CredentialModel(
            accessToken = "aaa",
            refreshToken = "bbb"
        )
    }

}