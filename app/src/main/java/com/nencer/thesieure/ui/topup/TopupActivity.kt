package com.nencer.thesieure.ui.topup

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseVMActivity
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.base.dialog.AlertType
import com.nencer.thesieure.base.dialog.CommonAlertDialog
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.customview.NumberTextWatcher
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.service.topup.TopupViewModel
import com.nencer.thesieure.service.user.model.response.UserInfo
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import com.nencer.thesieure.ui.main.history.HistoryModuleActivity
import com.nencer.thesieure.ui.topup.model.TopUp
import kotlinx.android.synthetic.main.activity_topup.*
import kotlinx.android.synthetic.main.content_navitation.btn_back_button
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopupActivity : BaseVMActivity() {
    private val muserViewModel: UserViewModel by viewModel()
    private val mTopupViewModel: TopupViewModel by viewModel()
    private val topups : ArrayList<TopUp> = arrayListOf()
    private val prices : ArrayList<String> = arrayListOf()

    private var amountAdapter: ArrayAdapter<String>? = null
    private var cardAdapter: ArrayAdapter<String>? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TopupActivity::class.java))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_topup

    override fun getViewModel(): BaseViewModel = muserViewModel

    override fun initView() {

        btn_back_button.setOnClickListener { onBackPressed() }

        btn_history.setOnClickListener {
            HistoryModuleActivity.start(this,5)
        }

        edtPrice.addTextChangedListener(NumberTextWatcher(edtPrice));

        muserViewModel.getUserInfo(Settings.Account.id,Settings.Account.token ?: "")
            .observe(this, Observer {
                val userInfo = UserInfo(it)
                tvInfo.text = "Ví: ${userInfo?.wallet?.number} - ${balance(userInfo?.wallet?.balance_decode!!.toBigDecimal()) + " " + userInfo?.wallet?.currency_code}"

            })

        mTopupViewModel.mtopuplist().observe(this, Observer {
            it.map{ jsonElement -> jsonElement.asJsonObject }.map{
                val id = it.optInt("id")
                val value = it.optString("value")
                val name = it.optString("name")
                val service_code = it.optString("service_code")
                val telco = it.optString("telco")

                topups.add(TopUp(id,value, name, service_code, telco))
                cardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,topups.map { it.name })
                spn_top_up!!.adapter = cardAdapter
                cardAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            val priceValues = topups[0].value.split(",")
            if (priceValues.isNullOrEmpty())prices.add("Không có dữ liệu" )
            else prices.addAll(topups[0].value.split(","))
            amountAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,prices)
            spn_amount!!.adapter = amountAdapter
            amountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            cardAdapter?.notifyDataSetChanged()
            amountAdapter?.notifyDataSetChanged()

            getPriceList(topups[0].service_code,prices[0])

        })


       action()

    }

    private fun action(){
        spn_top_up?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                prices.clear()
                val priceValues = topups[0].value.split(",")
                if (priceValues.isNullOrEmpty())prices.add("Không có dữ liệu")
                else  prices.addAll(topups[p2].value.split(","))
                if (prices.size == 1 && prices[0] == ""|| prices.size == 0){
                    fl_spn_prc.gone()
                    edtPrice.visible()
                }else{
                    fl_spn_prc.visible()
                    edtPrice.gone()
                }
                amountAdapter?.notifyDataSetChanged()

                getPriceList(topups[spn_top_up.selectedItemPosition].service_code,
                    prices[spn_amount.selectedItemPosition])
            }


        }

        spn_amount?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getPriceList(topups[spn_top_up.selectedItemPosition].service_code,
                    prices[spn_amount.selectedItemPosition])
            }

        }

        btn_pay.setOnClickListener {
            edt_phone.text.toString().trim().isEmpty().yes {
                notifyError("Bạn cần nhập số điện thoại")
            }.otherwise {
                    if (prices.size == 1 && prices[0] == ""|| prices.size == 0) {
                        mTopupViewModel.postmtopup(
                            edtPrice.text.toString().replace(",",""),
                            topups[spn_top_up.selectedItemPosition].service_code,
                            edt_phone.text.toString().trim(),
                            edtDes.text.toString().trim()
                        ).observe(this, Observer {
                            if (it.optInt("error_code") != 0) {
                                notifyError(it.optString("message"))
                            } else {
                                notifySuccess("Thành công!")
                            }

                        })
                    } else {
                        mTopupViewModel.postmtopup(
                            prices[spn_amount.selectedItemPosition],
                            edt_phone.text.toString().trim(),
                            topups[spn_top_up.selectedItemPosition].service_code,
                            edtDes.text.toString().trim()
                        ).observe(this, Observer {
                            val err_code = it.optInt("error_code")?:0
                            if (err_code != 0) {
                                notifyError(it.optString("message"))
                            } else {
                                notifySuccess("Thành công!")
                            }

                        })

                }
            }

        }

    }



    private fun getPriceList(telco: String,value: String){
        mTopupViewModel.mtopupprice(telco,value).observe(this, Observer {
            val pay_amount = it.optInt("pay_amount")
            val telco = it.optString("telco")

            txt_tel_co.text = telco
            txt_pay_amount.text = balance(pay_amount) + Settings.Account.currencyCode

        })
    }


    private fun notifyError(msg: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setType(AlertType.ERROR)
            .show()
    }

    private fun notifySuccess(msg: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setType(AlertType.SUCCESS)
            .show()
    }




}