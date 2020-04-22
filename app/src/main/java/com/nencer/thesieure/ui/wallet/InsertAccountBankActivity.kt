package com.nencer.thesieure.ui.wallet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseVMActivity
import com.nencer.thesieure.base.dialog.AlertType
import com.nencer.thesieure.base.dialog.CommonAlertDialog
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.ext.optString
import com.nencer.thesieure.service.wallet.WalletViewModel
import com.nencer.thesieure.service.wallet.response.BankUser
import kotlinx.android.synthetic.main.activity_insert_bank.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InsertAccountBankActivity: BaseVMActivity() {
    private val mViewModel: WalletViewModel by viewModel()

    override fun getLayoutId() :Int = R.layout.activity_insert_bank
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InsertAccountBankActivity::class.java))
        }
    }
    override fun initView() {

        btn_back_button.setOnClickListener{
            onBackPressed()
        }

        mViewModel.getListLocalBank().observe(this, Observer {
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, it.datas)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnBank!!.setAdapter(aa)
        })




        btninsert.setOnClickListener{
            postDeposit()
        }
    }

    override fun getViewModel(): BaseViewModel {
        return mViewModel
    }
    private fun postDeposit(){
        var account = edtstk.text.toString()
        var accountName = edtchutk.text.toString()

        var paygate_code= (spnBank.selectedItem as BankUser).paygate_code
        var numAtm = edtnumatm.text.toString()
        var nameBranch = edtchinhanh.text.toString()


        mViewModel.storebankuser(paygate_code.toString(),accountName.toString(),account.toString(), numAtm.toString() ,nameBranch.toString()).observe(this,
            Observer {
                val code = it.optString("error_code")
                val msg = it.optString("message")
                if("0".equals(code)){
                    registerSuccess(msg)
                }else{
                    if (msg.isNotEmpty()) {
                        notifyError(msg)
                    }
                }


            })
    }

    private fun notifyError(msg: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setType(AlertType.ERROR)
            .show()
    }

    private fun registerSuccess(msg: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setType(AlertType.SUCCESS)
            .setOnDismissListener {
                setResult(Activity.RESULT_OK)
                finish()
                this.finish()
            }
            .show()
    }

}