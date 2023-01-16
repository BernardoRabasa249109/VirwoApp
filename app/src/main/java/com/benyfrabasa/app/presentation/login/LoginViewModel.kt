package com.benyfrabasa.app.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyfrabasa.app.domain.useCases.GetUserByCredentials
import com.benyfrabasa.app.domain.useCases.SetUserSession
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
class LoginViewModel @Inject constructor(
    private val getUserByCredentials: GetUserByCredentials,
    private val setUserSession: SetUserSession
): ViewModel() {

    private val _effect: MutableSharedFlow<LoginViewEffect> by lazy { MutableSharedFlow() }
    val effect: SharedFlow<LoginViewEffect> by lazy { _effect.asSharedFlow() }

    private val _state: MutableStateFlow<LoginState> by lazy { MutableStateFlow(LoginState()) }
    val state: StateFlow<LoginState> by lazy { _state.asStateFlow() }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SignIn -> {
                handleSignIn(event)
            }
            is LoginEvent.LoginInputEnableAction -> {
                viewModelScope.launch {
                    _state.emit(
                        LoginState(
                            loginButtonEnabled = event.email.isNotEmpty() && event.password.isNotEmpty()
                        )
                    )
                }
            }
            is LoginEvent.SignUpClicked -> {
                viewModelScope.launch {
                    _effect.emit(LoginViewEffect.SignUpNavigation)
                }
            }
        }
    }

    private fun handleSignIn(event: LoginEvent.SignIn) {
        val loginState = LoginState()
        if (!isValidEmail(event.email))
            loginState.emailFormatError = true
        if (event.password.length < INPUT_MIN_LENGTH)
            loginState.passwordLengthError = true
        if (loginState.emailFormatError || loginState.passwordLengthError) {
            viewModelScope.launch {
                _state.emit(loginState)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val userResult = getUserByCredentials.runUseCase(event.email, event.password)
                userResult?.let { user ->
                    setUserSession.runUseCase(user)
                    _effect.emit(LoginViewEffect.LoggedIn)
                } ?: let {
                    _state.emit(LoginState(loginError = true))
                }
            }
        }
    }
}
