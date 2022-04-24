package co.hmtamim.survey.ui.screens

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import co.nimblehq.common.extensions.toastShort
import co.hmtamim.survey.R
import co.hmtamim.survey.databinding.ActivityMainBinding
import co.hmtamim.survey.extension.provideViewModels
import co.hmtamim.survey.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val viewModel: MainViewModel by provideViewModels()
    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = { inflater -> ActivityMainBinding.inflate(inflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setupNavGraph()
        makeStatusBarTransparent()
        observers()
    }

    private fun observers() {
        viewModel.globalObserver.onRefreshTokenExpiredLogout.receiveAsFlow() bindTo ::performLogout
        viewModel.loggedOut bindTo ::onLogout
        viewModel.error bindTo toaster::display
    }

    private fun performLogout(isLogout: Boolean) {
        if (isLogout) {
            viewModel.clearEncryptedSharedPreferences()
        }
    }

    private fun onLogout(isLogout: Boolean) {
        if (isLogout) {
            toastShort(getString(R.string.msg_logged_out))
            setupNavGraph()
        }
    }

    private fun makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        else
            window.setDecorFitsSystemWindows(false)
    }

    private fun setupNavGraph() {
        navController = Navigation.findNavController(this, R.id.navHostFragment)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph_main)
        if (!viewModel.isLogged()) {
            graph.startDestination = R.id.loginFragment
            navController.graph = graph
        }
    }

    // hide keyboard on touch outside
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}
