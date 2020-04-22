package com.nencer.thesieure.ui.wallet

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseDataBindVMActivity
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.base.dialog.AlertType
import com.nencer.thesieure.base.dialog.CommonAlertDialog
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.databinding.ActivityTranferBinding
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import com.nencer.thesieure.service.wallet.model.WalletTranferModel
import kotlinx.android.synthetic.main.activity_tranfer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.customview.NumberTextWatcher
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.service.user.model.response.UserInfo
import com.nencer.thesieure.service.wallet.WalletViewModel
import com.nencer.thesieure.service.wallet.response.HistoryInfo
import com.nencer.thesieure.ui.main.history.HistoryModuleActivity
import com.nencer.thesieure.ui.main.history.adapter.wallet.HistoryTransferAdapter
import com.paginate.Paginate
import kotlinx.android.synthetic.main.activity_tranfer.btn_back_button


class TranferActivity : BaseDataBindVMActivity<ActivityTranferBinding>(), Paginate.Callbacks {

    private val mViewModel: WalletViewModel by viewModel()
    private val mUserViewModel: UserViewModel by viewModel ()
    private val mWalletTranferModel: WalletTranferModel by lazy { WalletTranferModel() }

    private var page = 0
    private var loading: Boolean = false
    private var hasLoadedAllItems = false
    private lateinit var adapterTransfer: HistoryTransferAdapter
    private val listHistoryCommon: MutableList<HistoryInfo> = mutableListOf()
    private val mWalletDepositModel: WalletViewModel by viewModel()

    companion object {
        var instance : TranferActivity? = null
        fun start(context: Context) {
            context.startActivity(Intent(context, TranferActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        mUserViewModel.getUserInfo(Settings.Account.id,Settings.Account.token ?: "")
            .observe(this, Observer {
                val userInfo = UserInfo(it)
                tvInfo.text = "Ví: ${userInfo?.wallet?.number} - ${balance(userInfo?.wallet?.balance_decode!!.toBigDecimal()) + " " + userInfo?.wallet?.currency_code}"

            })
    }
    override fun getLayoutId(): Int = R.layout.activity_tranfer

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun initView() {
        loadDataHistory()

        btn_tranfer.setOnClickListener {
            tranfer()
        }

        btn_back_button.setOnClickListener { onBackPressed() }

        btn_history.setOnClickListener {
            HistoryModuleActivity.start(this,1)
        }

        mUserViewModel.getUserInfo(Settings.Account.id,Settings.Account.token ?: "")
            .observe(this, Observer {
                val userInfo = UserInfo(it)
                tvInfo.text = "Ví: ${userInfo?.wallet?.number} - ${balance(userInfo?.wallet?.balance_decode!!.toBigDecimal()) + " " + userInfo?.wallet?.currency_code}"

            })

        edt_acc.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                mUserViewModel.getName(Settings.Account.token,s.toString()).observe(this@TranferActivity
                    , Observer {
                    val name = it.optString("name")
                    name.isNotEmpty().yes {
                        tv_user_name.visible()
                        tv_user_name.text = name
                    }.otherwise {
                        tv_user_name.gone()
                    }
                })
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        edt_amount.addTextChangedListener(NumberTextWatcher(edt_amount));
        edt_msg.setSingleLine(false);
        edt_msg.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

    }

    override fun initData() {
        mDataBind.viewModel = mWalletTranferModel
    }

    private fun tranfer() {
        val accName = edt_acc.text.toString().trim()
        val amount = edt_amount.text.toString().trim()
        val msg = edt_msg.text.toString().trim()



        accName.isEmpty().yes {
            notifyError("Không được để trống tên đăng nhập")
        }.otherwise {
                amount.isEmpty().yes {
                    notifyError("Bạn chưa nhập số tiền")
                }.otherwise {
                    msg.isEmpty().yes {
                        notifyError("Bạn chưa nhập nội dung")
                    }.otherwise {
                        mViewModel.tranfer(accName, amount.replace(",",""), msg).observe(this,
                            Observer {
                                val msg = it.optString("message")
                                val code = it.optString("error_code")
                                if("0".equals(code)){
                                    if (msg.isNotEmpty()) {
                                        registerSuccess(msg , "")
                                    }
                                }else{
                                    if (msg.isNotEmpty()) {
                                        notifyError(msg)
                                    }
                                }


                            })

                    }
                }

        }
    }

    override fun onLoadMore() {
        loadDataHisWallet(true)
    }

    override fun isLoading(): Boolean = loading

    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems


    private fun loadDataHisWallet(loadMore: Boolean) {
        if (loading) {
            return
        }

        loading = true

        if (!loadMore) {
            page = 1
        }


        mWalletDepositModel.history("Transfer", page).observe(
            this,
            Observer { historyResponse ->
                loading = false
                if(historyResponse.data != null){
                    hasLoadedAllItems = historyResponse.data.size < 10
                    listHistoryCommon.addAll(historyResponse.data)
                    adapterTransfer.setListItems(listHistoryCommon)
                    page += 1
                }

            })


    }
    private fun loadDataHistory() {
        listHistoryCommon.clear()
        adapterTransfer =
            HistoryTransferAdapter(
                object :
                    OnItemClickListener<HistoryInfo> {
                    override fun onClick(item: HistoryInfo, position: Int) {
                    }
                })
        loadDataHisWallet(false)

    }
    private fun notifyError(msg: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setType(AlertType.ERROR)
            .show()
    }

    private fun registerSuccess(msg: String, mesdata: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setMessage(mesdata)
            .setType(AlertType.SUCCESS)
            .setOnDismissListener {
//                loadDataHistory()
                HistoryModuleActivity.start(this,1,true)
            }
            .show()
    }


}