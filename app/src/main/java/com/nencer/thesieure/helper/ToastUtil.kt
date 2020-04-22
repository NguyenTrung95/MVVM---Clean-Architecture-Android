package com.nencer.thesieure.helper

import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.nencer.thesieure.AppContext

object ToastUtil {

    private var toast: Toast? = null
    val context
        get() = AppContext
    fun show(text: String) {
        if (text.isEmpty()) return
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        //NIENLM: centered the bg_text_white_grey inside toast.
        val v = toast!!.view.findViewById<View>(android.R.id.message) as TextView
        v.gravity = Gravity.CENTER
        if (toast != null && toast!!.view != null && !toast!!.view.isShown)
            toast!!.show()
    }

    fun show(@StringRes textId: Int) {
        show(context.getString(textId))
    }
}
