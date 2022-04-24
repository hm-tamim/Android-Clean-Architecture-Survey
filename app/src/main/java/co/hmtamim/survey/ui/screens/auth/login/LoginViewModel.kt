package co.hmtamim.survey.ui.screens.auth.login

import androidx.lifecycle.viewModelScope
import co.nimblehq.common.extensions.isEmailValid
import co.hmtamim.survey.R
import co.hmtamim.survey.data.resource.ResourcesProvider
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.usecase.LoginUseCase
import co.hmtamim.survey.domain.usecase.TokenUseCases
import co.hmtamim.survey.extension.ifNotNull
import co.hmtamim.survey.ui.base.BaseViewModel
import co.hmtamim.survey.ui.base.NavigationEvent
import co.hmtamim.survey.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenUseCases: TokenUseCases,
    private val resourcesProvider: ResourcesProvider,
    dispatchers: DispatchersProvider,
) : BaseViewModel(dispatchers) {

    fun login(email: String, password: String) {

        var error = ""
        if (!email.isEmailValid())
            error = resourcesProvider.getString(R.string.error_invalid_email)
        else if (password.isEmpty())
            error = resourcesProvider.getString(R.string.error_password)

        if (error.isNotEmpty()) {
            viewModelScope.launch {
                _error.emit(error)
            }
            return
        }

        execute {
            showLoading()
            when (val result = loginUseCase.execute(LoginUseCase.Param(email, password))) {
                is ApiResponse.Success -> {
                    var hasError = true
                    result.data.let {
                        ifNotNull(it.accessToken, it.refreshToken) { access, refresh ->
                            hasError = false
                            execute {
                                tokenUseCases.saveAccessTokenUseCase.execute(access)
                                tokenUseCases.saveRefreshTokenUseCase.execute(refresh)
                                _navigator.emit(NavigationEvent.Home)
                            }
                        }
                    }
                    if (hasError)
                        _error.emit(resourcesProvider.getString(R.string.error_generic))
                }
                is ApiResponse.Failure -> {
                    _error.emit(result.message)
                }
            }
            hideLoading()
        }
    }

    fun navigateToForgotPassword() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.ForgotPassword)
        }
    }
}
