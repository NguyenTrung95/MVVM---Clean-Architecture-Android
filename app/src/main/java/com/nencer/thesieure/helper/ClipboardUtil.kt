
package com.nencer.thesieure.helper

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.nencer.thesieure.App


object ClipboardUtil {
    fun copyToClipBoard(activity: Activity, label: String, value: String) {
        val clipboard: ClipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, value)
        clipboard.setPrimaryClip(clip)
    }

    fun getFromClipBoard(activity: Activity): String?{
        val clipboard: ClipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        return clip?.getItemAt(0)?.text.toString()
    }

    fun getFromClipBoard(): String? {
        val clipboard = App.get().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = clipboard?.primaryClip
        return clip?.getItemAt(0)?.text.toString()
    }
}