package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakeSurveyRepository
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.repository.SurveyRepository
import co.hmtamim.survey.domain.usecase.FetchSurveysUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class FetchSurveysUseCaseTest {

    private lateinit var fakeSurveyRepository: FakeSurveyRepository
    private val mockRepository = mockk<SurveyRepository>(relaxed = true)

    private lateinit var fetchSurveysUseCase: FetchSurveysUseCase
    private lateinit var negativeFetchSurveysUseCase: FetchSurveysUseCase

    @Before
    fun setup() {

        fakeSurveyRepository = FakeSurveyRepository()
        fetchSurveysUseCase = FetchSurveysUseCase(fakeSurveyRepository)
        negativeFetchSurveysUseCase = FetchSurveysUseCase(mockRepository)

        // insert dummy data
        val list = mutableListOf<SurveyModel>()
        for (i in 1..10) {
            list.add(
                SurveyModel(i.toString(), i.toString(), i.toString(), i.toString())
            )
        }
        list.shuffle()
        runBlocking {
            fakeSurveyRepository.insertSurveys(list)
        }
    }

    @Test
    fun `Fetch survey list from Fake remote source`() = runTest {
        when (val result = fetchSurveysUseCase.execute()) {
            is ApiResponse.Success ->
                assert(result.data.size == 10) { "Expected size is 20, actual: ${result.data.size}" }
            is ApiResponse.Failure -> assert(false)
        }
    }

    @Test
    fun `Fetch survey list with error response`() = runTest {
        coEvery {
            mockRepository.fetchSurveyList()
        } coAnswers {
            ApiResponse.failure("error")
        }
        when (val result = negativeFetchSurveysUseCase.execute()) {
            is ApiResponse.Success -> assert(false)
            is ApiResponse.Failure -> assert(true)
        }
    }

}