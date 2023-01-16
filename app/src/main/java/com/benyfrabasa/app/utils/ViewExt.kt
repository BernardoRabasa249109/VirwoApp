package com.benyfrabasa.app.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.simpleTextChangedListener(callback: (String?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            callback(p0?.toString())
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(p0: Editable?) = Unit
    })
}
