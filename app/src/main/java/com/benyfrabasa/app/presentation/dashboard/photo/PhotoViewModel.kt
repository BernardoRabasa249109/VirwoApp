package com.benyfrabasa.app.presentation.dashboard.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor() : ViewModel() {
    private val _effect: MutableSharedFlow<PhotoViewEffect> by lazy { MutableSharedFlow() }
    val effect: SharedFlow<PhotoViewEffect> by lazy { _effect.asSharedFlow() }

    private val _state: MutableStateFlow<PhotoState> by lazy { MutableStateFlow(PhotoState()) }
    val state: StateFlow<PhotoState> by lazy { _state.asStateFlow() }

    fun onEvent(event: PhotoEvent) {
        viewModelScope.launch {
            when (event) {
                is PhotoEvent.TakePhoto -> {
                    _effect.emit(PhotoViewEffect.OpenCamera)
                }
                is PhotoEvent.DrawPhoto -> {
                    _state.emit(
                        PhotoState(
                            takePicture = false,
                            image = event.image
                        )
                    )
                }
            }
        }
    }
}
