package co.hmtamim.survey.ui.screens.auth.forgotPassword

import androidx.lifecycle.viewModelScope
import co.nimblehq.common.extensions.isEmailValid
import co.hmtamim.survey.R
import co.hmtamim.survey.data.resource.ResourcesProvider
import co.hmtamim.survey.domain.base.ApiResponse
import co.hmtamim.survey.domain.usecase.ForgotPasswordUseCase
import co.hmtamim.survey.ui.base.BaseViewModel
import co.hmtamim.survey.ui.base.NavigationEvent
import co.hmtamim.survey.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel(dispatchers) {

    private val _onResponse = MutableStateFlow("")
    val onResponse: StateFlow<String> get() = _onResponse

    fun forgotPassword(email: String) {
        execute {
            if (!email.isEmailValid()) {
                _error.emit(resourcesProvider.getString(R.string.error_invalid_email))
                return@execute
            }

            showLoading()
            when (val result = forgotPasswordUseCase.execute(email)) {
                is ApiResponse.Success -> {
                    _onResponse.value = result.data
                    viewModelScope.launch {
                        _navigator.emit(NavigationEvent.GoBack)
                    }
                }
                is ApiResponse.Failure -> {
                    _error.emit(result.message)
                }
            }
            hideLoading()
        }
    }
}
