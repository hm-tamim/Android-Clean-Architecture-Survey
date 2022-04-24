package co.hmtamim.survey.ui.base

import co.hmtamim.survey.domain.model.SurveyModel

sealed class NavigationEvent {
    data class Preview(val bundle: SurveyModel) : NavigationEvent()
    object Details : NavigationEvent()
    object Home : NavigationEvent()
    object ForgotPassword : NavigationEvent()
    object GoBack : NavigationEvent()
    object Login : NavigationEvent()
}
