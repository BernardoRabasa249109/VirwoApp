package com.benyfrabasa.app.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.benyfrabasa.app.R
import com.benyfrabasa.app.databinding.ActivityLoginBinding
import com.benyfrabasa.app.presentation.dashboard.HomeActivity
import com.benyfrabasa.app.presentation.signup.SignUpActivity
import com.benyfrabasa.app.utils.simpleTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is LoginViewEffect.LoggedIn -> {
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }
                        is LoginViewEffect.SignUpNavigation -> {
                            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    renderState(state)
                }
            }
        }
        setListeners()
    }

    private fun renderState(loginState: LoginState) = with(binding) {
        loginState.apply {
            loginButton.isEnabled = loginButtonEnabled
            errorMessageView.isVisible = loginError
            errorMessageView.text = getString(R.string.error_login_credentials)
            errorEmailMessage.isVisible = emailFormatError
            errorPasswordMessage.isVisible = passwordLengthError
        }
    }

    private fun setListeners() = with(binding) {
        emailEditText.simpleTextChangedListener { string ->
            string?.let {
                viewModel.onEvent(
                    LoginEvent.LoginInputEnableAction(it, passwordEditText.text.toString())
                )
            }
        }
        passwordEditText.simpleTextChangedListener { string ->
            string?.let {
                viewModel.onEvent(
                    LoginEvent.LoginInputEnableAction(emailEditText.text.toString(), it)
                )
            }
        }
        loginButton.setOnClickListener {
            viewModel.onEvent(
                LoginEvent.SignIn(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            )
        }
        signInLink.setOnClickListener {
            viewModel.onEvent(
                LoginEvent.SignUpClicked
            )
        }
    }
}
