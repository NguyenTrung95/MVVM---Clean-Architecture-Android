/*
 * Created by Nguyen Trung@mail: nguyennguyentrungtnt@gmail.com on 2019-09-03 ..
 */
package com.nencer.thesieure.base.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.nencer.thesieure.R
import com.nencer.thesieure.ext.otherwise
import com.nencer.thesieure.ext.yes
import com.nencer.thesieure.helper.ToastUtil
import kotlinx.android.synthetic.main.dialog_verify.*
import kotlin.system.exitProcess

class VerifyDialog(context: Context, onClickListener: (otp: String) -> Unit) : Dialog(context) {

    init {
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window?.run {
            setBackgroundDrawable(ColorDrawable(0))
        } ?: exitProcess(0)

        setContentView(R.layout.dialog_verify)

        findViewById<Button>(R.id.dialog_delete_warning_cancel_button).setOnClickListener { cancel() }
        findViewById<Button>(R.id.dialog_delete_warning_confirm_button).setOnClickListener { view ->
            edt_verify.text.toString().isEmpty().yes {
                ToastUtil.show("Bạn cần nhập mã xác thực")
            }.otherwise {
                onClickListener.invoke(edt_verify.text.toString())
            }
            dismiss()
        }

    }
}