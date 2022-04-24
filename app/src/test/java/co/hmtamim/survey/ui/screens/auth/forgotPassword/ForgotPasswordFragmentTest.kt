package co.hmtamim.survey.ui.screens.auth.forgotPassword

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.hmtamim.survey.databinding.FragmentForgetPasswordBinding
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

@HiltAndroidTest
class ForgotPasswordFragmentTest :
    BaseFragmentTest<ForgotPasswordFragment, FragmentForgetPasswordBinding>() {

    private val mockViewModel = mockk<ForgotPasswordViewModel>(relaxed = true)

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing HomeFragment, its views display Reset button`() {
        launchFragment()
        fragment.binding.btnReset.tag.toString() shouldBe "Reset"
    }

    @Test
    fun `When initializing HomeFragment, its views display Email edit text`() {
        launchFragment()
        fragment.binding.etEmail shouldNotBe null
    }

    @Test
    fun `When Reset button is clicked, it should call forgotPassword method in ViewModel`() {
        launchFragment()
        val email = "email@domaon.com"
        fragment.binding.etEmail.setText(email)
        fragment.binding.btnReset.callOnClick()

        verify {
            mockViewModel.forgotPassword(email)
        }
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<ForgotPasswordFragment>(
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
