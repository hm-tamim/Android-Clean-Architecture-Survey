package co.hmtamim.survey.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import co.hmtamim.survey.domain.repository.PrefRepository
import co.hmtamim.survey.test.FakeAndroidKeyStore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@SmallTest
class PrefRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Inject
    lateinit var pefRepository: PrefRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun clearTestPref() = runTest {
        pefRepository.clearStorage()
    }

    @Test
    fun `Save and get access token`() = runTest {
        pefRepository.saveAccessToken("abc")
        assert(pefRepository.getAccessToken() == "abc")
    }

    @Test
    fun `Save and get refresh token`() = runTest {
        pefRepository.saveRefreshToken("abc")
        assert(pefRepository.getRefreshToken() == "abc")
    }

    @Test
    fun `Clear storage`() = runTest {
        pefRepository.clearStorage()
        assert(pefRepository.getRefreshToken() == null)
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

}