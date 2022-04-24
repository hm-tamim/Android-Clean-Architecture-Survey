package co.hmtamim.survey.ui.screens.survey.details

import androidx.lifecycle.viewModelScope
import co.hmtamim.survey.ui.base.BaseViewModel
import co.hmtamim.survey.ui.base.NavigationEvent
import co.hmtamim.survey.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {
    fun navigateToBack()
}

@HiltViewModel
class SurveyDetailsViewModel @Inject constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), Output {

    override fun navigateToBack() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.GoBack)
        }
    }

}
