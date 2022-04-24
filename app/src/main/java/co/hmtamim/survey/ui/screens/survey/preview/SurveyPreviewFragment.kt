package co.hmtamim.survey.ui.screens.survey.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import co.hmtamim.survey.databinding.FragmentSurveyPreviewBinding
import co.hmtamim.survey.extension.provideViewModels
import co.hmtamim.survey.ui.base.BaseFragment
import co.hmtamim.survey.ui.screens.MainNavigator
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SurveyPreviewFragment : BaseFragment<FragmentSurveyPreviewBinding>() {

    @Inject
    lateinit var navigator: MainNavigator
    private val viewModel: SurveyPreviewViewModel by provideViewModels()
    private val args: SurveyPreviewFragmentArgs by navArgs()
    private var animationShown = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSurveyPreviewBinding
        get() = { inflater, container, attachToParent ->
            FragmentSurveyPreviewBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        binding.btnBack.setOnClickListener {
            viewModel.navigateToBack()
        }

        binding.btnStartSurvey.setOnClickListener {
            viewModel.navigateToDetails()
        }

        with(args.bundle) {
            with(binding) {
                tvTitle.text = title
                tvDescription.text = description
                ivBackground.transitionName = coverImageUrl

                Glide.with(ivBackground.context)
                    .asBitmap()
                    .load(coverImageUrl)
                    .into(ivBackground)

                if (!animationShown) {
                    ivBackground.animate()
                        .setDuration(1200)
                        .scaleX(1.7f)
                        .start()

                    ivBackground.animate()
                        .setDuration(1200)
                        .scaleY(1.7f)
                        .start()

                    animationShown = true
                }
            }
        }
    }

    override fun bindViewModel() {
        viewModel.navigator bindTo navigator::navigate
    }

}
