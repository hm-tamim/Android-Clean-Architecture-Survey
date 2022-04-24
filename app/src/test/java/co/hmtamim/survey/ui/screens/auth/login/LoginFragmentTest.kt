package co.hmtamim.survey.ui.screens.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import co.hmtamim.survey.databinding.FragmentLoginBinding
import co.hmtamim.survey.test.TestNavigatorModule.mockMainNavigator
import co.hmtamim.survey.test.getPrivateProperty
import co.hmtamim.survey.test.replace
import co.hmtamim.survey.ui.BaseFragmentTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class LoginFragmentTest : BaseFragmentTest<LoginFragment, FragmentLoginBinding>() {

    private val mockViewModel = mockk<LoginViewModel>(relaxed = true)

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("co.hmtamim.survey", appContext.packageName)
    }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When login button is clicked, it should call login() method with email, pass from binding`() {
        launchFragment()
        fragment.binding.etEmail.setText("email@domain.com")
        fragment.binding.etPassword.setText("1234")
        fragment.binding.btnLogin.callOnClick()
        verify {
            mockViewModel.login("email@domain.com", "1234")
        }
    }

    @Test
    fun `When Forgot button is clicked, it should call navigateToForgotPassword method in ViewModel`() {
        launchFragment()
        fragment.binding.tvForgotPassword.callOnClick()
        verify {
            mockViewModel.navigateToForgotPassword()
        }
    }

    @Test
    fun `When initializing LoginFragment, its views display Email EditText`() {
        launchFragment()
        fragment.binding.etEmail.hint.toString() shouldBe "Email"
    }

    @Test
    fun `When initializing LoginFragment, its views display Password EditText`() {
        launchFragment()
        fragment.binding.etPassword.hint.toString() shouldBe "Password"
    }

    @Test
    fun `When initializing LoginFragment, its views display login button`() {
        launchFragment()
        fragment.binding.btnLogin.text.toString() shouldBe "Log in"
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<LoginFragment>(
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
