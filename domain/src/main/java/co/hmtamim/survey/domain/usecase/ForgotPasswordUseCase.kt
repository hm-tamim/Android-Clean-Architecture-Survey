package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend fun execute(email: String): ApiResponse<String> {
        if (email.isEmpty())
            return ApiResponse.failure("Invalid email")
        return repository.forgotPassword(email)
    }
}
