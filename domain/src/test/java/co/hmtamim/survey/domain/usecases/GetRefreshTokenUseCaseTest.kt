package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakePrefRepository
import co.hmtamim.survey.domain.usecase.GetRefreshTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetRefreshTokenUseCaseTest {

    private lateinit var fakePrefRepository: FakePrefRepository
    private lateinit var getRefreshTokenUseCase: GetRefreshTokenUseCase

    @Before
    fun setup() {
        fakePrefRepository = FakePrefRepository()
        getRefreshTokenUseCase = GetRefreshTokenUseCase(fakePrefRepository)

        // insert dummy data
        runBlocking {
            fakePrefRepository.saveRefreshToken("abc")
        }
    }

    @Test
    fun `Get refresh token`() = runTest {
        assert(getRefreshTokenUseCase.execute() == "abc")
    }

}