package co.hmtamim.survey.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import co.hmtamim.survey.data.mapper.ForgotPassResponseMapper
import co.hmtamim.survey.data.mapper.LoginResponseMapper
import co.hmtamim.survey.data.repositoryimpl.AuthRepositoryImpl
import co.hmtamim.survey.data.response.ForgotPasswordResponse
import co.hmtamim.survey.data.response.LoginResponse
import co.hmtamim.survey.data.service.ApiService
import co.hmtamim.survey.data.util.ResponseHandler
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.repository.AuthRepository
import co.hmtamim.survey.domain.usecase.LoginUseCase
import co.hmtamim.survey.test.FakeAndroidKeyStore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@SmallTest
class AuthRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Inject
    lateinit var loginResponseMapper: LoginResponseMapper

    @Inject
    lateinit var forgotPassResponseMapper: ForgotPassResponseMapper

    @Inject
    lateinit var responseHandler: ResponseHandler

    private val mockApiService = mockk<ApiService>(relaxed = true)
    private lateinit var authRepository: AuthRepository

    @Before
    fun init() {
        hiltRule.inject()

        authRepository = AuthRepositoryImpl(
            mockApiService,
            loginResponseMapper,
            forgotPassResponseMapper,
            responseHandler
        )
    }


    @Test
    fun `Login, success response`() = runTest {
        coEvery {
            mockApiService.login(any())
        } coAnswers {
            Response.success(
                LoginResponse(
                    LoginResponse.Data(
                        LoginResponse.Data.Attributes(
                            accessToken = "abc",
                            refreshToken = "xyz"
                        )
                    )
                )
            )
        }

        val response = authRepository.login(
            LoginUseCase.Param(
                "email@domain.com",
                "1234"
            )
        )

        assert(
            response is ApiResponse.Success &&
                    response.data.accessToken == "abc" &&
                    response.data.refreshToken == "xyz"
        )
    }


    @Test
    fun `Login, error response`() = runTest {
        coEvery {
            mockApiService.login(any())
        } coAnswers {
            throw Exception("error")
        }

        val response = authRepository.login(
            LoginUseCase.Param(
                "email@domain.com",
                "1234"
            )
        )

        assert(response is ApiResponse.Failure)
    }

    @Test
    fun `Forgot password, success response`() = runTest {
        coEvery {
            mockApiService.forgotPassword(any())
        } coAnswers {
            Response.success(
                ForgotPasswordResponse(
                    ForgotPasswordResponse.Meta(
                        "success"
                    )
                )
            )
        }

        val response = authRepository.forgotPassword("email@domain.com")

        assert(response is ApiResponse.Success && response.data == "success")
    }

    @Test
    fun `Forgot password, error response`() = runTest {
        coEvery {
            mockApiService.forgotPassword(any())
        } coAnswers {
            throw Exception()
        }

        val response = authRepository.forgotPassword("email@domain.com")

        assert(response is ApiResponse.Failure)
    }


    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

}