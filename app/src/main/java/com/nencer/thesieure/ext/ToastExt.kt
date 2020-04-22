package com.nencer.thesieure.ext

import android.widget.Toast
import com.nencer.thesieure.AppContext

fun successToast(success: String) {
    Toast.makeText(AppContext, success, Toast.LENGTH_SHORT).show()
}

fun successToast(success: Int) {
    Toast.makeText(AppContext, success, Toast.LENGTH_SHORT).show()
}

fun warningToast(warning: String) {
    Toast.makeText(AppContext, warning, Toast.LENGTH_SHORT).show()
}

fun warningToast(warning: Int) {
    Toast.makeText(AppContext, warning, Toast.LENGTH_SHORT).show()
}

fun errorToast(error: String) {
    Toast.makeText(AppContext, error, Toast.LENGTH_SHORT).show()
}

fun errorToast(error: Int) {
    Toast.makeText(AppContext, error, Toast.LENGTH_SHORT).show()
}
