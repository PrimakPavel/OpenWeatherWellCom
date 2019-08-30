package com.pavelprymak.openweatherwellcom.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Context.hideKeyboardFrom(view: View) {
    try {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (ignore: Exception) {
    }

}

fun Context.showInputMethod(view: View) {
    try {
        view.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    } catch (ignore: Exception) {
    }

}