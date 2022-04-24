package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.repository.PrefRepository
import javax.inject.Inject

class GetRefreshTokenUseCase @Inject constructor(
    private val repository: PrefRepository,
) {

    fun execute(): String? {
        return try {
            repository.getRefreshToken()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
