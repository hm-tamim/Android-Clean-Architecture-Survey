package co.hmtamim.survey.domain.usecase

import co.hmtamim.survey.domain.repository.PrefRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenUseCases @Inject constructor(
    prefRepository: PrefRepository
) {
    val getAccessTokenUseCase = GetAccessTokenUseCase(prefRepository)
    val saveAccessTokenUseCase = SaveAccessTokenUseCase(prefRepository)
    val getRefreshTokenUseCase = GetRefreshTokenUseCase(prefRepository)
    val saveRefreshTokenUseCase = SaveRefreshTokenUseCase(prefRepository)
}