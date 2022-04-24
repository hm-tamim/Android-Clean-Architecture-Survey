package co.hmtamim.survey.domain.repository

import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.CredentialModel
import co.hmtamim.survey.domain.usecase.LoginUseCase

interface AuthRepository {
    suspend fun login(body: LoginUseCase.Param): ApiResponse<CredentialModel>
    suspend fun forgotPassword(email: String): ApiResponse<String>
    suspend fun refreshToken(refreshToken: String): CredentialModel
}
