package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakePrefRepository
import co.hmtamim.survey.domain.usecase.GetAccessTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetAccessTokenUseCaseTest {

    private lateinit var fakePrefRepository: FakePrefRepository
    private lateinit var getAccessTokenUseCase: GetAccessTokenUseCase

    @Before
    fun setup() {
        fakePrefRepository = FakePrefRepository()
        getAccessTokenUseCase = GetAccessTokenUseCase(fakePrefRepository)

        // insert dummy data
        runBlocking {
            fakePrefRepository.saveAccessToken("abc")
        }
    }

    @Test
    fun `Get access token`() = runTest {
        assert(getAccessTokenUseCase.execute() == "abc")
    }

}