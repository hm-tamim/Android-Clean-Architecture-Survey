package co.hmtamim.survey.data.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.domain.repository.SurveyRepository
import co.hmtamim.survey.test.FakeAndroidKeyStore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@SmallTest
class SurveyRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: SurveyRepository

    @Before
    fun init() {
        hiltRule.inject()
        runBlocking {
            repository.deleteAll()
        }
    }

    @After
    fun tearDown() {
        runBlocking {
            repository.deleteAll()
        }
    }

    @Test
    fun testInsertSurveysToDatabase() {

        // data is inserted to a test database
        val expectedEntity = SurveyModel(
            "1",
            "title",
            "description",
            "cover_image_url",
        )

        runTest {
            repository.insertSurveys(listOf(expectedEntity))
            val latch = CountDownLatch(1)
            val job = launch(Dispatchers.IO) {
                repository.getSurveyList().collectLatest {
                    it.size shouldBe 1
                    latch.countDown()
                }
            }
            latch.await()
            job.cancel()
        }
    }

    @Test
    fun testRemoveOldSurveyFromDatabase() {
        val expectedEntity = SurveyModel(
            "1",
            "title",
            "description",
            "cover_image_url",
        )

        runTest {
            repository.deleteAll()
            repository.insertSurveys(listOf(expectedEntity))

            repository.deleteOldSurveys(listOf("0"))
            val latch = CountDownLatch(1)
            val job = launch(Dispatchers.IO) {
                repository.getSurveyList().collectLatest {
                    it.size shouldBe 0
                    latch.countDown()
                }
            }
            latch.await()
            job.cancel()
        }
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

}