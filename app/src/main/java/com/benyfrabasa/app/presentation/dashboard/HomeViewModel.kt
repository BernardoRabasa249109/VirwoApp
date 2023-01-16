package com.benyfrabasa.app.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyfrabasa.app.domain.useCases.GetUserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val getUserSession: GetUserSession
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> by lazy { MutableStateFlow(HomeState()) }
    val state: StateFlow<HomeState> by lazy { _state.asStateFlow() }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetUserSession -> {
                getUserSession.runUseCase()?.also {
                    viewModelScope.launch {
                        _state.emit(
                            HomeState(
                                userName = it.user,
                                email = it.email
                            )
                        )
                    }
                }
            }
        }
    }
}
