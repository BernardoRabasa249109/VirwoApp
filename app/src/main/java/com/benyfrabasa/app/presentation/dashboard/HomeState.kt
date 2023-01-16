package com.benyfrabasa.app.presentation.dashboard

data class HomeState(
    var userName: String? = null,
    var email: String? = null
)

sealed class HomeEvent {
    object GetUserSession : HomeEvent()
}
