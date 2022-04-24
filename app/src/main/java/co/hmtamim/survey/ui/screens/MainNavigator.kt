package co.hmtamim.survey.ui.screens

import androidx.fragment.app.Fragment
import co.hmtamim.survey.R
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.ui.base.BaseNavigator
import co.hmtamim.survey.ui.base.BaseNavigatorImpl
import co.hmtamim.survey.ui.base.NavigationEvent
import co.hmtamim.survey.ui.screens.auth.login.LoginFragmentDirections
import co.hmtamim.survey.ui.screens.home.HomeFragmentDirections
import co.hmtamim.survey.ui.screens.survey.preview.SurveyPreviewFragmentDirections
import javax.inject.Inject

interface MainNavigator : BaseNavigator

class MainNavigatorImpl @Inject constructor(
    fragment: Fragment
) : BaseNavigatorImpl(fragment), MainNavigator {

    override val navHostFragmentId = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.Preview -> navigateToPreview(event.bundle)
            is NavigationEvent.Details -> navigateToDetails()
            is NavigationEvent.Home -> navigateToHomeFromLogin()
            is NavigationEvent.GoBack -> navigateUp()
            is NavigationEvent.ForgotPassword -> navigateToForgotPassword()
            is NavigationEvent.Login -> navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.homeFragment -> navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToForgotPassword() {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.loginFragment -> navController.navigate(
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToPreview(bundle: SurveyModel) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.homeFragment -> navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToSurveyPreviewFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToDetails() {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.surveyPreviewFragment -> navController.navigate(
                SurveyPreviewFragmentDirections.actionSurveyPreviewFragmentToSurveyDetailsFragment()
            )
            else -> unsupportedNavigation()
        }
    }

    private fun navigateToHomeFromLogin() {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.loginFragment -> navController.navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            )
            else -> unsupportedNavigation()
        }
    }

}
