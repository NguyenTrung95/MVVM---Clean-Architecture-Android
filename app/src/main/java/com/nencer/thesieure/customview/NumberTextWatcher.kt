package com.nencer.thesieure.customview

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class NumberTextWatcher : TextWatcher {

    private var mEditing: Boolean = false

    constructor(edt: EditText) {
        mEditing = false
    }

    constructor() {
        mEditing = false
    }

    @Synchronized
    override fun afterTextChanged(editable: Editable) {
        if (editable.length == 0) {
            return
        }
        if (!mEditing) {
            mEditing = true

            val string = editable.toString()

            val s = string.replace(",", "").split("".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val stringBuilder = StringBuilder()
            val newString: String
            var j = 0
            for (i in s.indices.reversed()) {
                stringBuilder.append(s[j])
                if (i % 3 == 0 && i != 0) {
                    stringBuilder.append(",")
                }
                j++
            }
            if (stringBuilder[0] == ',') {
                newString = stringBuilder.substring(1)
            } else {
                newString = stringBuilder.toString()
            }
            editable.replace(0, editable.length, newString)

            mEditing = false
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }
}