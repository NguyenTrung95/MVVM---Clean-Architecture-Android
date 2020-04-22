/*
 * Created by Nguyen Trung@mail: nguyennguyentrungtnt@gmail.com on 2019-09-03 ..
 */
package com.nencer.thesieure.base.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.nencer.thesieure.R
import com.nencer.thesieure.ext.gone
import kotlinx.android.synthetic.main.dialog_editer.btn_action
import kotlinx.android.synthetic.main.dialog_editer.tv_title
import kotlinx.android.synthetic.main.dialog_password_change.*

class PasswordEditerDialog : BaseDialogFragment() {
    private var title: String?=""
    private var button_name : String?= ""
    private var onDismissListener: ((PasswordEditerDialog) -> Unit)? = null
    private var onDataTextChanged: ((String,String,String) -> Unit)?= null

    override fun getLayoutResId() = R.layout.dialog_password_change

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (title.isNullOrBlank()) {
            tv_title.gone()
        } else {
            tv_title.text = title
        }

        if (button_name.isNullOrBlank()) {
            btn_action.gone()
        } else {
            btn_action.text = button_name
        }

        btn_action.setOnClickListener {
            onDataTextChanged?.invoke(edt_old_password.text.toString(),
            edt_new_password.text.toString(),edt_re_password.text.toString())
        }



    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke(this)
    }

    class Builder(private val manager: FragmentManager) {

        private var title: String? = null
        private var button_name: String? = null
        private var cancelable = true
        private var onDismissListener: ((PasswordEditerDialog) -> Unit)? = null
        private var onDataTextChanged: ((String,String,String) -> Unit)?= null


        fun setTitle(title: String?) = apply { this.title = title }

        fun setButtonName(message: String?) = apply { this.button_name = message }


        fun setCancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun setOnDismissListener(onDismissListener: (PasswordEditerDialog) -> Unit) = apply { this.onDismissListener = onDismissListener }

        fun setOnDataTextChangeListener( onDataTextChanged: ((String,String,String) -> Unit)) = apply { this.onDataTextChanged = onDataTextChanged }

        fun show(): PasswordEditerDialog {
            val dialog = PasswordEditerDialog()
            dialog.title = title
            dialog.button_name = button_name
            dialog.isCancelable = cancelable
            dialog.onDismissListener = onDismissListener
            dialog.onDataTextChanged = onDataTextChanged
            dialog.show(manager, null)
            return dialog
        }
    }

}