package co.hmtamim.survey.ui

import android.content.Context
import co.hmtamim.survey.R

fun Throwable.userReadableMessage(context: Context): String {
    // TODO implement user readable message
    return context.getString(R.string.error_generic)
}
