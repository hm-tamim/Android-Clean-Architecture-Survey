package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakePrefRepository
import co.hmtamim.survey.domain.usecase.ClearStorageUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ClearStorageUseCaseTest {

    private lateinit var fakePrefRepository: FakePrefRepository
    private lateinit var clearStorageUseCase: ClearStorageUseCase

    @Before
    fun setup() {
        fakePrefRepository = FakePrefRepository()
        clearStorageUseCase = ClearStorageUseCase(fakePrefRepository)

    }

    @Test
    fun `Clear storage`() = runTest {
        clearStorageUseCase.execute()
        assert(fakePrefRepository.getAccessToken() == null)
    }

}