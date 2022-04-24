package co.hmtamim.survey.ui.screens

import co.hmtamim.survey.R
import co.hmtamim.survey.data.observer.GlobalObserver
import co.hmtamim.survey.data.resource.ResourcesProvider
import co.hmtamim.survey.domain.usecase.ClearStorageUseCase
import co.hmtamim.survey.domain.usecase.GetAccessTokenUseCase
import co.hmtamim.survey.domain.usecase.UseCaseResult
import co.hmtamim.survey.ui.base.BaseViewModel
import co.hmtamim.survey.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatchers: DispatchersProvider,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val clearStorageUseCase: ClearStorageUseCase,
    private val resourcesProvider: ResourcesProvider,
    val globalObserver: GlobalObserver
) : BaseViewModel(dispatchers) {

    private val _loggedOut = Channel<Boolean>()
    val loggedOut = _loggedOut.receiveAsFlow()

    fun isLogged(): Boolean = getAccessTokenUseCase.execute() != null

    fun clearEncryptedSharedPreferences() {
        execute {
            when (val task = clearStorageUseCase.execute()) {
                is UseCaseResult.Success -> _loggedOut.send(true)
                is UseCaseResult.Error -> _error.emit(task.exception.message.orEmpty())
                else -> _error.emit(resourcesProvider.getString(R.string.error_logout))
            }
        }
    }

}
