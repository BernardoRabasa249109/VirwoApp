package com.benyfrabasa.app.presentation.login

data class LoginState(
    var loginButtonEnabled: Boolean = false,
    var loginError: Boolean = false,
    var emailFormatError: Boolean = false,
    var passwordLengthError: Boolean = false,
)

sealed class LoginViewEffect {
    object LoggedIn : LoginViewEffect()
    object SignUpNavigation : LoginViewEffect()
}

sealed class LoginEvent {
    data class LoginInputEnableAction(val email: String, val password: String) : LoginEvent()
    data class SignIn(val email: String, val password: String) : LoginEvent()
    object SignUpClicked : LoginEvent()
}
