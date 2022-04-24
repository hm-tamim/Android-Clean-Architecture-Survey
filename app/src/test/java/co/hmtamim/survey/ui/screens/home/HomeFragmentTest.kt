package co.hmtamim.survey.ui.screens.home

import android.app.AlertDialog
import android.os.Looper
import co.hmtamim.survey.databinding.FragmentHomeBinding
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowAlertDialog
import java.text.SimpleDateFormat
import java.util.*


@HiltAndroidTest
class HomeFragmentTest : BaseFragmentTest<HomeFragment, FragmentHomeBinding>() {

    private val mockViewModel = mockk<HomeViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing HomeFragment, its views display Next button`() {
        launchFragment()
        fragment.binding.btnAction.tag.toString() shouldBe "Next Button"
    }

    @Test
    fun `When initializing HomeFragment, its views display Today text`() {
        launchFragment()
        fragment.binding.tvToday shouldNotBe null
    }

    @Test
    fun `When initializing HomeFragment, its views display correct Current Date`() {
        launchFragment()
        fragment.binding.tvDate shouldNotBe null

        val actualDate = fragment.binding.tvDate.text.toString()
        val formatter = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault())
        val expectedDate = formatter.format(Calendar.getInstance().time)

        assert(actualDate == expectedDate)
    }

    @Test
    fun `When floating button is clicked, it should call navigateToPreview method in ViewModel`() {
        launchFragment()
        fragment.binding.btnAction.callOnClick()
        verify {
            mockViewModel.navigateToPreview(any())
        }
    }

    @Test
    fun `When logout is confirmed, it should call requestLogout method of ViewModel`() {
        launchFragment()
        fragment.binding.ivUserImage.callOnClick()

        (ShadowAlertDialog.getLatestAlertDialog() as AlertDialog)
            .getButton(AlertDialog.BUTTON_POSITIVE)
            .performClick()

        shadowOf(Looper.getMainLooper()).idle()

        verify {
            mockViewModel.requestLogout()
        }
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<HomeFragment>(
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
