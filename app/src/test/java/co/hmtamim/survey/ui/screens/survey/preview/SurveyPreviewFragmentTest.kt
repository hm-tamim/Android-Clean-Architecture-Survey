package co.hmtamim.survey.ui.screens.survey.preview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.hmtamim.survey.databinding.FragmentSurveyPreviewBinding
import co.hmtamim.survey.domain.model.SurveyModel
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
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SurveyPreviewFragmentTest :
    BaseFragmentTest<SurveyPreviewFragment, FragmentSurveyPreviewBinding>() {

    private val mockViewModel = mockk<SurveyPreviewViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When Take Survey is clicked, it should call navigateToDetails`() = runBlocking {
        launchFragment()
        fragment.binding.btnStartSurvey.callOnClick()
        verify { mockViewModel.navigateToDetails() }
    }

    @Test
    fun `When Back button is clicked, it should call navigateUp`() = runBlocking {
        launchFragment()
        fragment.binding.btnBack.callOnClick()
        verify { mockViewModel.navigateToBack() }
    }

    @Test
    fun `When initializing HomeFragment, it binds data from arguments`() = runBlocking {
        launchFragment()
        assert(fragment.binding.tvTitle.text.toString() == "title test")
        assert(fragment.binding.tvDescription.text.toString() == "description test")
    }

    @Test
    fun `When initializing HomeFragment, its views display Take Survey button`() {
        launchFragment()
        fragment.binding.btnStartSurvey.text.toString() shouldBe "Take Survey"
    }

    @Test
    fun `When initializing HomeFragment, its views display Title text`() {
        launchFragment()
        fragment.binding.tvTitle shouldNotBe null
    }

    @Test
    fun `When initializing HomeFragment, its views display Description text`() {
        launchFragment()
        fragment.binding.tvDescription shouldNotBe null
    }


    private fun launchFragment() {
        launchFragmentInHiltContainer<SurveyPreviewFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
                navigator = mockMainNavigator
            },
            fragmentArgs = SurveyPreviewFragmentArgs(
                SurveyModel(
                    "1",
                    "title test",
                    "description test",
                    "http://..."
                )
            ).toBundle()
        ) {
            fragment = this
            fragment.navigator.shouldNotBeNull()
        }
    }
}
