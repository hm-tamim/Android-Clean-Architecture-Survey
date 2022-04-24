package co.hmtamim.survey.ui.screens.survey.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.hmtamim.survey.databinding.FragmentSurveyDetailsBinding
import co.hmtamim.survey.test.TestNavigatorModule.mockMainNavigator
import co.hmtamim.survey.test.getPrivateProperty
import co.hmtamim.survey.test.replace
import co.hmtamim.survey.ui.BaseFragmentTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class SurveyDetailsFragmentTest :
    BaseFragmentTest<SurveyDetailsFragment, FragmentSurveyDetailsBinding>() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val mockViewModel = mockk<SurveyDetailsViewModel>(relaxed = true)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing HomeFragment, its views display Title text`() {
        launchFragment()
        fragment.binding.tvTitle shouldNotBe null
    }

    @Test
    fun `When initializing HomeFragment, its views display Go Back button`() {
        launchFragment()
        fragment.binding.btnBack.text.toString() shouldBe "Go back"
    }

    @Test
    fun `When Go Back button is clicked, it should call navigateUp`() = runBlocking {
        launchFragment()
        fragment.binding.btnBack.callOnClick()
        verify { mockViewModel.navigateToBack() }
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<SurveyDetailsFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
                navigator = mockMainNavigator
            }
        ) {
            fragment = this
            fragment.navigator.shouldNotBeNull()
        }
    }

}
