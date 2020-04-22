package com.nencer.thesieure.ui.wallet

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseDataBindVMActivity
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.base.dialog.AlertType
import com.nencer.thesieure.base.dialog.CommonAlertDialog
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.customview.NumberTextWatcher
import com.nencer.thesieure.databinding.ActivityDepositBinding
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optJsonObject
import com.nencer.thesieure.ext.optString
import com.nencer.thesieure.service.user.model.response.UserInfo
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import com.nencer.thesieure.service.wallet.WalletViewModel
import com.nencer.thesieure.service.wallet.model.WalletDepositModel
import com.nencer.thesieure.service.wallet.response.HistoryInfo
import com.nencer.thesieure.service.wallet.response.Paygate
import com.nencer.thesieure.ui.main.history.HistoryModuleActivity
import com.nencer.thesieure.ui.main.history.adapter.wallet.HistoryDepositAdapter
import kotlinx.android.synthetic.main.activity_deposit.*
import kotlinx.android.synthetic.main.activity_deposit.btn_back_button
import kotlinx.android.synthetic.main.activity_deposit.btn_deposit
import kotlinx.android.synthetic.main.activity_deposit.btn_history
import kotlinx.android.synthetic.main.activity_deposit.tvInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class DepositActivity : BaseDataBindVMActivity<ActivityDepositBinding>(){


    private val mViewModel: WalletViewModel by viewModel()
    private val muserViewModel: UserViewModel by viewModel()
    private val mWalletModel: WalletDepositModel by lazy { WalletDepositModel() }

    private lateinit var adapterDeposit: HistoryDepositAdapter
    private val listHistoryCommon: MutableList<HistoryInfo> = mutableListOf()


    override fun getLayoutId(): Int = R.layout.activity_deposit

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DepositActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        muserViewModel.getUserInfo(Settings.Account.id,Settings.Account.token ?: "")
            .observe(this, Observer {
                val userInfo = UserInfo(it)
                tvInfo.text = "Ví: ${userInfo?.wallet?.number} - ${balance(userInfo?.wallet?.balance_decode!!.toBigDecimal()) + " " + userInfo?.wallet?.currency_code}"

            })
    }
    override fun initView() {

        btn_back_button.setOnClickListener {
            onBackPressed()
        }

        mViewModel.getListPaygate("deposit").observe(this, Observer {
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, it.datas)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnMethod!!.setAdapter(aa)
        })


        muserViewModel.getUserInfo(Settings.Account.id,Settings.Account.token ?: "")
            .observe(this, Observer {
                val userInfo = UserInfo(it)
                tvInfo.text = "Ví: ${userInfo?.wallet?.number} - ${balance(userInfo?.wallet?.balance_decode!!.toBigDecimal()) + " " + userInfo?.wallet?.currency_code}"

            })


        btn_deposit.setOnClickListener {
            postDeposit()
        }

        btn_history.setOnClickListener {
            HistoryModuleActivity.start(this,3)
        }
        loadDataHistory()
        edtMoney.addTextChangedListener(NumberTextWatcher(edtMoney));

    }

    override fun initData() {
        mDataBind.viewModel = mWalletModel
    }

    override fun getViewModel(): BaseViewModel {
        return mViewModel
    }

    private fun postDeposit() {
        val amount = edtMoney.text.toString()
        val id = (spnMethod.selectedItem as Paygate).id

        if (TextUtils.isEmpty(amount)) {
            notifyError("Bạn phải nhập số tiền !")
            return
        }
        if (id == null || id == 0) {
            notifyError("Bạn phải chọn hình thức thanh toán !")
            return
        }

        mViewModel.postdepositwallet(amount.replace(",",""), id.toString()).observe(this,
            Observer {
                val code = it.optString("error_code")
                val msg = it.optString("message")
                if ("0".equals(code)) {
                    val detail = it.getAsJsonObject("detail")
                    val mes = detail.optString("order_code")
                    val currency_code = detail.optString("currency_code")
                    val net_amount = detail.optInt("net_amount", 0)
                    val des = "<b>Mã giao dịch </b> " + "<font size=\"2\" color=\"blue\">" + mes+ "</font>"+ "<br/>" +
                            "<b>Số tiền </b>" + "<font size=\"2\" color=\"blue\">" + "${balance(net_amount?: 0)} ${currency_code}" + "</font>"

                    val don_hang = detail.optString("order_code")
                    val vi_nhan = detail.optString("payee_wallet")

                    val so_tien = detail.optInt("net_amount")
                    val phi = detail.optInt("fees")

                    val hinh_thuc = detail.optString("bank_code")
                    val ngay_tao = detail.optString("created_at")

                    val trang_thai = detail.optString("status")
                    val bank_info = detail.optJsonObject("bank_info")
                    val bank_name = bank_info?.optString("name")

                    val chu_tai_khoan = bank_info?.optString("account_name")
                    val so_tk = bank_info?.optString("account_number")

                    val so_the_atm = bank_info?.optString("account_card_number")
                    val chi_nhanh = bank_info?.optString("account_branch")

                    val noi_dung = detail.optString("description")
                    val chuyen_so_tien = detail.optInt("pay_amount")

                    DepositDetailActivity.start(this,don_hang = don_hang,
                        vi_nhan = vi_nhan,so_tien = so_tien.toString(),phi = phi.toString(),hinh_thuc = hinh_thuc,
                        ngay_tao = ngay_tao,trang_thai = trang_thai,chu_tk = chu_tai_khoan?:"",stk = so_tk?:"",so_atm = so_the_atm?:"",
                        chi_nhanh = chi_nhanh?:"",noi_dung_chuyen = noi_dung,so_tien_chuyen = chuyen_so_tien.toString() , bank_name = bank_name.toString() , isDeposit = true)
                } else {
                    if (msg.isNotEmpty()) {
                        notifyError(msg)
                    }
                }


            })
    }

    private fun loadDataHistory() {
        listHistoryCommon.clear()
        adapterDeposit =
            HistoryDepositAdapter(
                object :
                    OnItemClickListener<HistoryInfo> {
                    override fun onClick(item: HistoryInfo, position: Int) {
                    }
                })

    }


/*
    private fun loadDataHisWallet(loadMore: Boolean) {
        if (loading) {
            return
        }

        loading = true

        if (!loadMore) {
            page = 1
        }


        mWalletDepositModel.history("Deposit", page).observe(
            this,
            Observer { historyResponse ->
                loading = false
                hasLoadedAllItems = historyResponse.data.size < 10
                listHistoryCommon.addAll(historyResponse.data)
                adapterDeposit.setListItems(listHistoryCommon)
                page += 1
            })


    }
*/

    private fun notifyError(msg: String) {
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle(msg)
            .setType(AlertType.ERROR)
            .show()
    }

    private fun registerSuccess(msg: String, des : String) {
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle(msg)
            .setMessage(des)
            .setType(AlertType.SUCCESS)
            .setOnDismissListener {
                HistoryModuleActivity.start(this,3)

            }
            .show()
    }

}