package com.benyfrabasa.app.presentation.signup

data class SignUpState(
    var signUpButtonEnabled: Boolean = false,
    var userLengthError: Boolean = false,
    var emailFormatError: Boolean = false,
    var passwordLengthError: Boolean = false,
)

sealed class SignUpViewEffect {
    object SignedIn : SignUpViewEffect()
}

sealed class SignUpEvent {
    data class SignUpInputEnableAction(val user: String, val email: String, val password: String) : SignUpEvent()
    data class SingUp(val user: String, val email: String, val password: String) : SignUpEvent()
}
