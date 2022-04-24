package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.repository.PrefRepository
import javax.inject.Inject

class ClearStorageUseCase @Inject constructor(
    private val repository: PrefRepository,
) {
    suspend fun execute(): UseCaseResult<String?> {
        return try {
            repository.clearStorage()
            UseCaseResult.Success("Success")
        } catch (e: Exception) {
            e.printStackTrace()
            UseCaseResult.Error(e)
        }
    }
}
