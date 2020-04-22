/*
 * Created by Nguyen Trung@mail: nguyennguyentrungtnt@gmail.com on 2019-09-03 ..
 */
package com.nencer.thesieure.base.dialog.editer

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.nencer.thesieure.R
import com.nencer.thesieure.base.dialog.BaseDialogFragment
import com.nencer.thesieure.ext.gone
import kotlinx.android.synthetic.main.dialog_editer.*

class EditerDialog : BaseDialogFragment() {
    private var title: String?=""
    private var button_name : String?= ""
    private var  values : MutableList<Triple<String,String,Boolean>> = mutableListOf()
    private var onDismissListener: ((EditerDialog) -> Unit)? = null

    private val adapter : EditerAdapter by lazy { EditerAdapter() }
    override fun getLayoutResId() = R.layout.dialog_editer

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

        adapter.setListItems(values)
        rcyEditer.adapter = adapter

        btn_action.setOnClickListener {

        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke(this)
    }

    class Builder(private val manager: FragmentManager) {

        private var title: String? = null
        private var button_name: String? = null
        private var  values : MutableList<Triple<String,String,Boolean>> = mutableListOf()
        private var cancelable = true
        private var onDismissListener: ((EditerDialog) -> Unit)? = null

        fun setTitle(title: String?) = apply { this.title = title }

        fun setButtonName(message: String?) = apply { this.button_name = message }

        fun setValues(values : MutableList<Triple<String,String,Boolean>>) = apply { this.values.addAll(values) }

        fun setCancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun setOnDismissListener(onDismissListener: (EditerDialog) -> Unit) = apply { this.onDismissListener = onDismissListener }

        fun show(): EditerDialog {
            val dialog = EditerDialog()
            dialog.title = title
            dialog.button_name = button_name
            dialog.values .addAll(values)
            dialog.isCancelable = cancelable
            dialog.onDismissListener = onDismissListener
            dialog.show(manager, null)
            return dialog
        }
    }

}