package com.nencer.thesieure.base.dialog

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.nencer.thesieure.ext.gone
import com.nencer.thesieure.R
import kotlinx.android.synthetic.main.dialog_common_alert.*

/**
 * Created by nguyentrung on 2019-06-22
 */
class CommonAlertDialog : BaseDialogFragment() {

    private var title: String? = null
    private var message: String? = null
    private var type: AlertType? = null
    private var buttons: List<Pair<String, OnClickListener?>>? = null
    private var onDismissListener: ((CommonAlertDialog) -> Unit)? = null

    override fun getLayoutResId() = R.layout.dialog_common_alert

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context

        if (title.isNullOrBlank()) {
            tvTitle.gone()
        } else {
            tvTitle.text = title
        }

        if (message.isNullOrBlank()) {
            tvMessage.gone()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvMessage.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvMessage.setText(Html.fromHtml(message));
            }
        }

        if (type == null) {
            imvType.gone()
        } else {
            imvType.setImageResource(
                    when (type) {
                        AlertType.WARNING -> R.drawable.ic_warning_wallet
                        AlertType.ERROR -> R.drawable.ic_exit
                        else -> R.drawable.ic_success
                    }
            )
        }

        buttons?.forEach {
            val label = it.first
            val listener = it.second
            // create button
            val button = Button(context)
            button.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            val param = button.layoutParams as LinearLayout.LayoutParams
            val margin = context.resources.getDimension(R.dimen._4sdp).toInt()
            param.setMargins(margin, 0, margin, 0)
            button.layoutParams = param
            button.text = label
            button.background = AppCompatResources.getDrawable(context, R.drawable.bg_button_yellow_normal)
            button.setTextColor(ContextCompat.getColor(context, R.color.white_1))
            button.isAllCaps = false
            // add action
            button.setOnClickListener {
                dismiss()
                listener?.invoke(this)
            }
            llButtons.addView(button)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke(this)
    }

    class Builder(private val manager: FragmentManager) {

        private var title: String? = null
        private var message: String? = null
        private var type: AlertType? = null
        private var cancelable = true
        private val buttons = mutableListOf<Pair<String, OnClickListener?>>()
        private var onDismissListener: ((CommonAlertDialog) -> Unit)? = null

        fun setTitle(title: String?) = apply { this.title = title }

        fun setMessage(message: String?) = apply { this.message = message }

        fun setType(type: AlertType?) = apply { this.type = type }

        fun setCancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun addButton(label: String, onClickListener: OnClickListener? = null) = apply { buttons.add(label to onClickListener) }

        fun setOnDismissListener(onDismissListener: (CommonAlertDialog) -> Unit) = apply { this.onDismissListener = onDismissListener }

        fun show():CommonAlertDialog {
            val dialog = CommonAlertDialog()
            dialog.title = title
            dialog.message = message
            dialog.type = type
            dialog.isCancelable = cancelable
            dialog.buttons = buttons
            dialog.onDismissListener = onDismissListener
            dialog.show(manager, null)
            return dialog
        }
    }
}

enum class AlertType {
    WARNING, ERROR, SUCCESS
}

typealias OnClickListener = (CommonAlertDialog) -> Unit