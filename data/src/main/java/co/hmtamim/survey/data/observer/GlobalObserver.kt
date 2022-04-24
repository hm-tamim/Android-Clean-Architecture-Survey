package co.hmtamim.survey.data.observer

import kotlinx.coroutines.channels.Channel

class GlobalObserver {
    val onRefreshTokenExpiredLogout = Channel<Boolean>()
}