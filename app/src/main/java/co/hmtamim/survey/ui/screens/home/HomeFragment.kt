package co.hmtamim.survey.ui.screens.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import co.nimblehq.common.extensions.gone
import co.nimblehq.common.extensions.visible
import co.hmtamim.survey.R
import co.hmtamim.survey.data.constant.DATE_FORMAT
import co.hmtamim.survey.databinding.FragmentHomeBinding
import co.hmtamim.survey.domain.model.SurveyModel
import co.hmtamim.survey.extension.getCurrentDateTime
import co.hmtamim.survey.extension.provideViewModels
import co.hmtamim.survey.extension.setTextAnimation
import co.hmtamim.survey.extension.toString
import co.hmtamim.survey.lib.IsLoading
import co.hmtamim.survey.ui.base.BaseFragment
import co.hmtamim.survey.ui.screens.MainNavigator
import co.hmtamim.survey.ui.screens.home.adapter.SurveyAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), SwipeRefreshLayout.OnRefreshListener {


    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: HomeViewModel by provideViewModels()
    private lateinit var adapter: SurveyAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        setupTitleTextSwitcher()
        setupViewPager()
        clickListeners()
        binding.tvDate.text = getCurrentDateTime().toString(DATE_FORMAT)
        binding.srlContainer.setOnRefreshListener(this)
    }

    private fun clickListeners() {
        binding.btnAction.setOnClickListener {
            viewModel.navigateToPreview(viewModel.getSelectedSurveyModel())
        }

        binding.ivUserImage.bringToFront()
        binding.ivUserImage.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.msg_logout_confirmation))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.text_yes)) { _, _ ->
                viewModel.requestLogout()
            }
            .setNegativeButton(getString(R.string.text_no)) { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun setupTitleTextSwitcher() {
        binding.tsTitle.setFactory {
            TextView(
                ContextThemeWrapper(
                    requireActivity(),
                    R.style.TitleTextStyle
                ), null, 0
            )
        }

        val inAnim = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_down
        )

        inAnim.duration = 250
        binding.tsTitle.inAnimation = inAnim
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViewPager() {
        if (!::adapter.isInitialized)
            adapter = SurveyAdapter()

        with(binding) {
            vpContainer.adapter = adapter
            TabLayoutMediator(tlIndicator, vpContainer) { tab, position ->

            }.attach()

            vpContainer.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.setSelectedSurveyPosition(position)
                    super.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    // disable pull to refresh when scrolling horizontally
                    binding.srlContainer.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
                }
            })
        }
    }

    override fun bindViewModel() {
        viewModel.surveyListModels bindTo ::displaySurveyList
        viewModel.selectedSurveyPosition bindTo ::displaySelectedSurveyItem
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
    }

    private fun displaySelectedSurveyItem(position: Int) {
        if (position < 0)
            return
        val model = viewModel.surveyListModels.value[position]
        with(model) {
            with(binding) {
                Glide.with(requireContext())
                    .load(coverImageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivBackground)
                ivBackground.transitionName = coverImageUrl
                tsTitle.setText(title)
                tsTitle.invalidate()
                tsTitle.forceLayout()
                tsTitle.refreshDrawableState()
                tvDescription.setTextAnimation(description, 150)
            }
        }
    }

    private fun displaySurveyList(surveyListModels: List<SurveyModel>) {
        adapter.items = surveyListModels
    }

    private fun bindLoading(isLoading: IsLoading) {
        if (isLoading) {
            binding.clContainer.gone()
            binding.skeleton.slSkeleton.visible()
            binding.skeleton.slSkeleton.startShimmerAnimation()
        } else {
            binding.clContainer.visible()
            binding.skeleton.slSkeleton.gone()
            binding.skeleton.slSkeleton.stopShimmerAnimation()
        }
    }

    override fun onRefresh() {
        viewModel.fetchSurveyList()
        binding.srlContainer.isRefreshing = false
    }
}
