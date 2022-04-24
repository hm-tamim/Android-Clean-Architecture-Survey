package co.hmtamim.survey.ui.screens.survey.details

import android.view.LayoutInflater
import android.view.ViewGroup
import co.hmtamim.survey.databinding.FragmentSurveyDetailsBinding
import co.hmtamim.survey.extension.provideViewModels
import co.hmtamim.survey.ui.base.BaseFragment
import co.hmtamim.survey.ui.screens.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SurveyDetailsFragment : BaseFragment<FragmentSurveyDetailsBinding>() {

    @Inject
    lateinit var navigator: MainNavigator
    private val viewModel: SurveyDetailsViewModel by provideViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSurveyDetailsBinding
        get() = { inflater, container, attachToParent ->
            FragmentSurveyDetailsBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        binding.btnBack.setOnClickListener {
            viewModel.navigateToBack()
        }
    }

    override fun bindViewModel() {
        viewModel.navigator bindTo navigator::navigate
    }
}
