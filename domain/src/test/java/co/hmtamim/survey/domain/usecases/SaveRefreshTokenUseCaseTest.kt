package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakePrefRepository
import co.hmtamim.survey.domain.usecase.SaveRefreshTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SaveRefreshTokenUseCaseTest {

    private lateinit var fakePrefRepository: FakePrefRepository
    private lateinit var saveRefreshTokenUseCase: SaveRefreshTokenUseCase

    @Before
    fun setup() {
        fakePrefRepository = FakePrefRepository()
        saveRefreshTokenUseCase = SaveRefreshTokenUseCase(fakePrefRepository)

    }

    @Test
    fun `Save refresh token`() = runTest {
        saveRefreshTokenUseCase.execute("abc")
        assert(fakePrefRepository.getRefreshToken() == "abc")
    }

}