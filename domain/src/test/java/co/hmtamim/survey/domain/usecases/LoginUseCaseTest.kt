package co.hmtamim.survey.domain.usecases

import co.hmtamim.survey.domain.usecases.repository.FakeAuthRepository
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.repository.AuthRepository
import co.hmtamim.survey.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private val mockRepository = mockk<AuthRepository>(relaxed = true)
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var negativeLoginUseCase: LoginUseCase

    @Before
    fun setup() {
        authRepository = FakeAuthRepository()
        loginUseCase = LoginUseCase(authRepository)
        negativeLoginUseCase = LoginUseCase(mockRepository)
    }

    @Test
    fun `Login without email`() = runTest {

        val result = loginUseCase.execute(
            LoginUseCase.Param(
                "", "111"
            )
        )
        assert(result is ApiResponse.Failure)
    }

    @Test
    fun `Login without password`() = runTest {
        val result = loginUseCase.execute(
            LoginUseCase.Param(
                "email@domain.com", ""
            )
        )
        assert(result is ApiResponse.Failure)
    }

    @Test
    fun `Login with email and password`() = runTest {
        val result = loginUseCase.execute(
            LoginUseCase.Param(
                "email@domain.com", "1111"
            )
        )
        assert(result is ApiResponse.Success)
    }

    @Test
    fun `Login with error response`() = runTest {
        val params = LoginUseCase.Param(
            "email@domain.com", "1111"
        )

        coEvery {
            mockRepository.login(params)
        } coAnswers {
            ApiResponse.failure("error")
        }

        val result = negativeLoginUseCase.execute(
            LoginUseCase.Param(
                "email@domain.com", "1111"
            )
        )

        assert(result is ApiResponse.Failure)
    }

}