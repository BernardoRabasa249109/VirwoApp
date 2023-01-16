package com.benyfrabasa.app.presentation.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.benyfrabasa.app.R
import com.benyfrabasa.app.databinding.ActivitySignUpBinding
import com.benyfrabasa.app.utils.simpleTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is SignUpViewEffect.SignedIn -> {
                            Toast.makeText(
                                this@SignUpActivity,
                                getString(R.string.new_account_message),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
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

    private fun renderState(signUpState: SignUpState) = with(binding) {
        signUpState.apply {
            signUpButton.isEnabled = signUpButtonEnabled
            errorUserMessage.isVisible = userLengthError
            errorEmailMessage.isVisible = emailFormatError
            errorPasswordMessage.isVisible = passwordLengthError
        }
    }

    private fun setListeners() = with(binding) {
        userEditText.simpleTextChangedListener { string ->
            string?.let {
                viewModel.onEvent(
                    SignUpEvent.SignUpInputEnableAction(
                        it,
                        emailEditText.text.toString(),
                        passwordEditText.text.toString())
                )
            }
        }
        emailEditText.simpleTextChangedListener { string ->
            string?.let {
                viewModel.onEvent(
                    SignUpEvent.SignUpInputEnableAction(
                        userEditText.text.toString(),
                        it,
                        passwordEditText.text.toString()
                    )
                )
            }
        }
        passwordEditText.simpleTextChangedListener { string ->
            string?.let {
                viewModel.onEvent(
                    SignUpEvent.SignUpInputEnableAction(
                        userEditText.text.toString(),
                        emailEditText.text.toString(),
                        it
                    )
                )
            }
        }
        signUpButton.setOnClickListener {
            viewModel.onEvent(
                SignUpEvent.SingUp(
                    userEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            )
        }
    }
}
