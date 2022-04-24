package co.hmtamim.survey.ui.screens.auth.forgotPassword

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import co.nimblehq.common.extensions.gone
import co.nimblehq.common.extensions.visible
import co.hmtamim.survey.R
import co.hmtamim.survey.data.constant.NOTIFICATION_CHANNEL_ID
import co.hmtamim.survey.databinding.FragmentForgetPasswordBinding
import co.hmtamim.survey.extension.provideViewModels
import co.hmtamim.survey.lib.IsLoading
import co.hmtamim.survey.ui.base.BaseFragment
import co.hmtamim.survey.ui.screens.MainNavigator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgetPasswordBinding>() {

    @Inject
    lateinit var navigator: MainNavigator

    private val viewModel: ForgotPasswordViewModel by provideViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgetPasswordBinding
        get() = { inflater, container, attachToParent ->
            FragmentForgetPasswordBinding.inflate(inflater, container, attachToParent)
        }

    override fun setupView() {
        Glide.with(binding.ivBackground)
            .load(R.drawable.bg_team)
            .apply(bitmapTransform(BlurTransformation(55, 3)))
            .into(binding.ivBackground)

        binding.btnReset.setOnClickListener {
            val email: String = binding.etEmail.text.toString()
            viewModel.forgotPassword(email)
        }
    }

    override fun bindViewModel() {
        viewModel.error bindTo toaster::display
        viewModel.navigator bindTo navigator::navigate
        viewModel.showLoading bindTo ::bindLoading
        viewModel.onResponse bindTo ::onSuccessResponse
    }

    private fun onSuccessResponse(s: String) {
        if (s.isEmpty())
            return
        sendNotification(s)
    }

    private fun bindLoading(loading: IsLoading) {
        with(binding) {
            if (loading) {
                btnReset.gone()
                btnLoading.visible()
            } else {
                btnReset.visible()
                btnLoading.gone()
            }
        }
    }

    private fun sendNotification(body: String) {
        createNotificationChannel()
        var builder = NotificationCompat.Builder(requireContext(), NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.msg_check_email))
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(Calendar.getInstance().timeInMillis.toInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
