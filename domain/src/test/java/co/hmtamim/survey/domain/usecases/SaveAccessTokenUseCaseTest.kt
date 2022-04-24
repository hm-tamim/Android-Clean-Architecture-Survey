package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakePrefRepository
import co.hmtamim.survey.domain.usecase.SaveAccessTokenUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SaveAccessTokenUseCaseTest {

    private lateinit var fakePrefRepository: FakePrefRepository
    private lateinit var saveAccessTokenUseCase: SaveAccessTokenUseCase

    @Before
    fun setup() {
        fakePrefRepository = FakePrefRepository()
        saveAccessTokenUseCase = SaveAccessTokenUseCase(fakePrefRepository)

    }

    @Test
    fun `Save access token`() = runTest {
        saveAccessTokenUseCase.execute("abc")
        assert(fakePrefRepository.getAccessToken() == "abc")
    }

}