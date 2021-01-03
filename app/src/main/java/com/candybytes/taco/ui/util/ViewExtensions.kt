package com.candybytes.taco.ui.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

// To hide the soft keyboard
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}