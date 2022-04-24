package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakeSurveyRepository
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.usecase.InsertSurveysUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class InsertSurveysUseCaseTest {

    private lateinit var fakeSurveyRepository: FakeSurveyRepository
    private lateinit var insertSurveysUseCase: InsertSurveysUseCase

    @Before
    fun setup() {
        fakeSurveyRepository = FakeSurveyRepository()
        insertSurveysUseCase = InsertSurveysUseCase(fakeSurveyRepository)
    }

    @Test
    fun `Insert surveys`() = runTest {
        val model = SurveyModel(
            "1",
            "this is title",
            "description",
            "image url"
        )
        insertSurveysUseCase.execute(
            listOf(
                model
            )
        )

        assert(fakeSurveyRepository.getSurveyList().last().contains(model))
    }

}