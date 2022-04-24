package co.hmtamim.survey.extension

import android.view.View
import android.widget.TextView

fun TextView.setTextAnimation(
    text: String,
    duration: Long = 300,
    completion: (() -> Unit)? = null
) {
    fadeOut(duration, View.VISIBLE) {
        this.text = text
        fadeIn(duration) {
            completion?.let {
                it()
            }
        }
    }
}

fun View.fadeOut(
    duration: Long = 300,
    visibility: Int = View.INVISIBLE,
    completion: (() -> Unit)? = null
) {

    animate()
        .alpha(0.3f)
        .setDuration(duration)
        .withEndAction {
            this.visibility = visibility
            completion?.let {
                it()
            }
        }
}

fun View.fadeIn(duration: Long = 300, completion: (() -> Unit)? = null) {
    alpha = 0.3f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(duration)
        .withEndAction {
            completion?.let {
                it()
            }
        }
}