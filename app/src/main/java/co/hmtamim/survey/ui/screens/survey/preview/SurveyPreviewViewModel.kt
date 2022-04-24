package co.hmtamim.survey.ui.screens.survey.preview

import androidx.lifecycle.viewModelScope
import co.hmtamim.survey.ui.base.BaseViewModel
import co.hmtamim.survey.ui.base.NavigationEvent
import co.hmtamim.survey.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Output {
    fun navigateToDetails()
    fun navigateToBack()
}

@HiltViewModel
class SurveyPreviewViewModel @Inject constructor(
    dispatchers: DispatchersProvider
) : BaseViewModel(dispatchers), Output {

    override fun navigateToDetails() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.Details)
        }
    }

    override fun navigateToBack() {
        viewModelScope.launch {
            _navigator.emit(NavigationEvent.GoBack)
        }
    }

}
