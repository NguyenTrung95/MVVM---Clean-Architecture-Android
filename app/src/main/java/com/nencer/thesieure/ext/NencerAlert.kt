package com.nencer.thesieure.ext

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object MDAlert {
    fun alert(context: Context, title: String? = null, message: String? = null): AlertDialog.Builder {
        return AlertDialog.Builder(context).setTitle(title).setMessage(message)
    }
}
typealias DialogAction = (DialogInterface, Int) -> Unit

fun AlertDialog.Builder.defaultAction(title: String, action: DialogAction? = null): AlertDialog.Builder {
    return this.setPositiveButton(title) { dialog, which ->
        action?.invoke(dialog, which)
        dialog.dismiss()
    }
}

fun AlertDialog.Builder.cancelAction(title: String, action: DialogAction? = null): AlertDialog.Builder {
    return this.setNegativeButton(title) { dialog, which ->
        action?.invoke(dialog, which)
        dialog.dismiss()
    }
}