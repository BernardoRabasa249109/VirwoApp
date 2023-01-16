package com.benyfrabasa.app.utils

import android.util.Patterns

fun isValidEmail(target: CharSequence?): Boolean {
    return target?.let { Patterns.EMAIL_ADDRESS.matcher(target).matches() } ?: false
}
