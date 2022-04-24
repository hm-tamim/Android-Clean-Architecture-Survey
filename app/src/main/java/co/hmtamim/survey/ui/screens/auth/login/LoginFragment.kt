package co.hmtamim.survey.ui.screens.auth.login

import android.view.LayoutInflater
import android.view.ViewGroup
import co.nimblehq.common.extensions.gone
import co.nimblehq.common.extensions.visible
import co.hmtamim.survey.R
import co.hmtamim.survey.databinding.FragmentLoginBinding
import co.hmtamim.survey.extension.provideViewModels
import co.hmtamim.survey.lib.IsLoading
import co.hmtamim.survey.ui.base.BaseFragment
import co.hmtamim.survey.ui.screens.MainNavigator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    val viewModel: LoginViewModel by provideViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = { inflater, container, attachToParent ->
            FragmentLoginBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        Glide.with(binding.ivBackground)
            .load(R.drawable.bg_team)
            .apply(bitmapTransform(BlurTransformation(55, 3)))
            .into(binding.ivBackground)

        binding.btnLogin.setOnClickListener {
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.tvForgotPassword.setOnClickListener {
            viewModel.navigateToForgotPassword()
        }
    }

    override fun bindViewModel() {
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
        viewModel.showLoading bindTo ::bindLoading
    }

    private fun bindLoading(loading: IsLoading) {
        with(binding) {
            if (loading) {
                btnLogin.gone()
                btnLoading.visible()
            } else {
                btnLogin.visible()
                btnLoading.gone()
            }
        }
    }
}
