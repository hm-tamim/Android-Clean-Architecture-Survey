package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakeSurveyRepository
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.usecase.DeleteOldSurveysUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DeleteOldSurveyUseCaseTest {

    private lateinit var fakeSurveyRepository: FakeSurveyRepository
    private lateinit var deleteOldSurveysUseCase: DeleteOldSurveysUseCase

    @Before
    fun setup() {
        fakeSurveyRepository = FakeSurveyRepository()
        deleteOldSurveysUseCase = DeleteOldSurveysUseCase(fakeSurveyRepository)

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
    fun `Delete old surveys`() = runTest {
        // dummy list
        val toDeleteIds = mutableListOf<String>()
        for (i in 1..5)
            toDeleteIds.add(i.toString())

        deleteOldSurveysUseCase.execute(toDeleteIds)

        val sizeNow = fakeSurveyRepository.getSurveyList().last().size
        assert(
            sizeNow == 5
        ) { "Expected size is 5, actual: $sizeNow" }
    }

}