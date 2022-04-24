package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.repository.PrefRepository
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(private val repository: PrefRepository) {

    suspend fun execute(token: String): UseCaseResult<Unit> {
        return try {
            val inserted = repository.saveAccessToken(token)
            UseCaseResult.Success(inserted)
        } catch (e: Exception) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}
