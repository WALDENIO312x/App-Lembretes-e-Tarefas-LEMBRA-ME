package com.example.lembra_me.extensions

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("PT", "BR")

fun Date.format() : String {
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

/**
 * Usando segundo a explicação
 */
var TextInputLayout.text : String?
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }