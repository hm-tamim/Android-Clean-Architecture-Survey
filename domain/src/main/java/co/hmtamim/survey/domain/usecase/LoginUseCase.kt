package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.CredentialModel
import co.hmtamim.survey.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    data class Param(
        val email: String,
        val password: String,
    )

    suspend fun execute(params: Param): ApiResponse<CredentialModel> {
        if (params.email.isEmpty())
            return ApiResponse.failure("Invalid email")
        else if (params.password.isEmpty())
            return ApiResponse.failure("Invalid password")
        return repository.login(params)
    }
}
