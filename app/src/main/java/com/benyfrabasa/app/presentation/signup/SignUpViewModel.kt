package com.benyfrabasa.app.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyfrabasa.app.domain.model.User
import com.benyfrabasa.app.domain.useCases.InsertUser
import com.benyfrabasa.app.utils.INPUT_MIN_LENGTH
import com.benyfrabasa.app.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val insertUser: InsertUser
): ViewModel() {
    private val _effect: MutableSharedFlow<SignUpViewEffect> by lazy { MutableSharedFlow() }
    val effect: SharedFlow<SignUpViewEffect> by lazy { _effect.asSharedFlow() }

    private val _state: MutableStateFlow<SignUpState> by lazy { MutableStateFlow(SignUpState()) }
    val state: StateFlow<SignUpState> by lazy { _state.asStateFlow() }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUpInputEnableAction -> {
                viewModelScope.launch {
                    _state.emit(
                        SignUpState(
                            signUpButtonEnabled =
                                event.user.isNotEmpty() &&
                                event.email.isNotEmpty() &&
                                event.password.isNotEmpty()
                        )
                    )
                }
            }
            is SignUpEvent.SingUp -> {
                handleSignUp(event)
            }
        }
    }

    private fun handleSignUp(event: SignUpEvent.SingUp) {
        val signupState = SignUpState()
        if (event.user.length < INPUT_MIN_LENGTH)
            signupState.userLengthError = true
        if (!isValidEmail(event.email))
            signupState.emailFormatError = true
        if (event.password.length < INPUT_MIN_LENGTH)
            signupState.passwordLengthError = true
        if (signupState.userLengthError || signupState.emailFormatError || signupState.passwordLengthError) {
            viewModelScope.launch {
                _state.emit(signupState)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                insertUser.runUseCase(
                    User(
                        user = event.user,
                        email = event.email,
                        password = event.password
                    )
                )
                _effect.emit(SignUpViewEffect.SignedIn)
            }
        }
    }
}
