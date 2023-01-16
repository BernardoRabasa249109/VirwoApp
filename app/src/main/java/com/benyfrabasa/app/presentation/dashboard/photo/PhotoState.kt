package com.benyfrabasa.app.presentation.dashboard.photo

import android.graphics.Bitmap

data class PhotoState(
    val takePicture: Boolean = true,
    val image: Bitmap? = null,
)

sealed class PhotoViewEffect{
    object OpenCamera : PhotoViewEffect()
}

sealed class PhotoEvent {
    object TakePhoto : PhotoEvent()
    class DrawPhoto(val image: Bitmap) : PhotoEvent()
}
