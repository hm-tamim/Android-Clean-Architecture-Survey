package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.repository.PrefRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: PrefRepository,
) {

    fun execute(): String? {
        return try {
            repository.getAccessToken()
        } catch (e: Exception) {
            null
        }
    }
}
