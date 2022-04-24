package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakeAuthRepository
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.repository.AuthRepository
import co.hmtamim.survey.domain.usecase.ForgotPasswordUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class ForgotPasswordUseCaseTest {

    private lateinit var fakeAuthRepository: FakeAuthRepository
    private val mockRepository = mockk<AuthRepository>(relaxed = true)

    private lateinit var forgotPasswordUseCase: ForgotPasswordUseCase
    private lateinit var negativeForgotPasswordUseCase: ForgotPasswordUseCase

    @Before
    fun setup() {
        fakeAuthRepository = FakeAuthRepository()
        forgotPasswordUseCase = ForgotPasswordUseCase(fakeAuthRepository)
        negativeForgotPasswordUseCase = ForgotPasswordUseCase(mockRepository)
    }

    @Test
    fun `Request Forgot password without email`() = runTest {
        val result = forgotPasswordUseCase.execute(
            ""
        )
        assert(result is ApiResponse.Failure)
    }

    @Test
    fun `Request Forgot password with email`() = runTest {
        val result = forgotPasswordUseCase.execute(
            "email@domain.com"
        )
        assert(result is ApiResponse.Success)
    }

    @Test
    fun `Forget password with error response`() = runTest {
        val email = "email@domain.com"

        coEvery {
            mockRepository.forgotPassword(email)
        } coAnswers {
            ApiResponse.failure("error")
        }

        val result = negativeForgotPasswordUseCase.execute(email)

        assert(result is ApiResponse.Failure)
    }

}