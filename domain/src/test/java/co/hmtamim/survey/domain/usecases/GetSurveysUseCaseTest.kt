package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakeSurveyRepository
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.usecase.GetSurveysUseCase
import co.hmtamim.survey.domain.usecase.UseCaseResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetSurveysUseCaseTest {

    private lateinit var fakeSurveyRepository: FakeSurveyRepository
    private lateinit var getSurveysUseCase: GetSurveysUseCase

    @Before
    fun setup() {

        fakeSurveyRepository = FakeSurveyRepository()
        getSurveysUseCase = GetSurveysUseCase(fakeSurveyRepository)

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
    fun `Get survey list from Fake db`() = runTest {
        when (val result = getSurveysUseCase.execute()) {
            is UseCaseResult.Success ->
                assert(result.data.last().size == 10) { "Expected size is 20, actual: ${result.data.last().size}" }
            is UseCaseResult.Failure -> assert(false)
        }
    }

}