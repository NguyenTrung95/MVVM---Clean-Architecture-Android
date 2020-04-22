/*
 * Created by Nguyen Trung@mail: nguyennguyentrungtnt@gmail.com on 2019-09-03 ..
 */
package com.nencer.thesieure.base.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import com.nencer.thesieure.R
import com.nencer.thesieure.ext.gone
import com.nencer.thesieure.ext.otherwise
import com.nencer.thesieure.ext.visible
import com.nencer.thesieure.ext.yes
import com.nencer.thesieure.service.user.model.response.UserInfo
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import kotlinx.android.synthetic.main.dialog_editer.btn_action
import kotlinx.android.synthetic.main.dialog_editer.tv_title
import kotlinx.android.synthetic.main.dialog_info_change.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class InfoEditerDialog : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {
    private var title: String? = ""
    private var button_name: String? = ""
    private var onDismissListener: ((InfoEditerDialog) -> Unit)? = null
    private var onDataTextChanged: ((String, String, String, String, String, view: View) -> Unit)? =
        null
    private var info: UserInfo? = null
    private var genders = arrayListOf<String>("Nam", "Nữ")

    private var country: ArrayList<String> = arrayListOf()
    override fun getLayoutResId() = R.layout.dialog_info_change

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, genders)
        spn_gioi_tinh!!.adapter = adapter
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

        if (info != null) {
            val adapterQG = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, country)
            spn_quoc_gia!!.adapter = adapterQG
            adapterQG?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            if (info?.gender == "male") {
                spn_gioi_tinh.setSelection(0)
            } else spn_gioi_tinh.setSelection(1)


            edt_ngay_sinh.setText(info?.birthday)

            info?.email?.isNotEmpty()?.yes {
                edt_email.gone()
                tv_email.gone()
            }?.otherwise {
                edt_email.setText(info?.email)
                edt_email.visible()
                tv_email.visible()
            }


        }



        btn_action.setOnClickListener {
            onDataTextChanged?.invoke(
                genders[spn_gioi_tinh.selectedItemPosition],
                edt_ngay_sinh.text.toString(), country[spn_quoc_gia.selectedItemPosition],
                edt_mat_khau_c2.text.toString(),
                edt_email.text.toString(), view
            )
        }

        edt_ngay_sinh.setOnClickListener {
            SpinnerDatePickerDialogBuilder()
                .context(view.context)
                .callback(this)
                .defaultDate(1980, 0, 1)
                .build()
                .show()
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
        private var onDismissListener: ((InfoEditerDialog) -> Unit)? = null
        private var onDataTextChanged: ((String, String, String, String, String, View) -> Unit)? =
            null
        private var info: UserInfo? = null
        private var country: ArrayList<String> = arrayListOf()


        fun setTitle(title: String?) = apply { this.title = title }

        fun setButtonName(message: String?) = apply { this.button_name = message }

        fun setCancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun setOnDismissListener(onDismissListener: (InfoEditerDialog) -> Unit) =
            apply { this.onDismissListener = onDismissListener }

        fun setOnDataTextChangeListener(onDataTextChanged: ((String, String, String, String, String, View) -> Unit)) =
            apply { this.onDataTextChanged = onDataTextChanged }

        fun setValue(value: UserInfo? = null) = apply { this.info = value }
        fun setCountry(value: ArrayList<String>? = null) = apply { this.country.addAll(value?: arrayListOf()) }

        fun show(): InfoEditerDialog {
            val dialog = InfoEditerDialog()
            dialog.title = title
            dialog.button_name = button_name
            dialog.isCancelable = cancelable
            dialog.onDismissListener = onDismissListener
            dialog.onDataTextChanged = onDataTextChanged
            dialog.show(manager, null)
            dialog.info = info
            dialog.country.addAll(country)
            return dialog
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar: Calendar = GregorianCalendar(year, monthOfYear, dayOfMonth)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        edt_ngay_sinh.setText(simpleDateFormat.format(calendar.time))
    }


}