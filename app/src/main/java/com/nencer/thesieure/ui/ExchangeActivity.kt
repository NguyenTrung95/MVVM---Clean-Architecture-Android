package com.nencer.thesieure.ui

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
import com.nencer.thesieure.service.exchange.ExchangeViewModel
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.ui.main.home.model.Exchange
import com.nencer.thesieure.service.user.model.response.UserInfo
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import com.nencer.thesieure.ui.main.history.HistoryModuleActivity
import kotlinx.android.synthetic.main.activity_exchange.*
import kotlinx.android.synthetic.main.content_navitation.btn_back_button
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeActivity : BaseVMActivity() {
    private val muserViewModel: UserViewModel by viewModel()
    private val mExchangeViewModel: ExchangeViewModel by viewModel()
    private val cards : ArrayList<Exchange> = arrayListOf()
    private val prices : ArrayList<String> = arrayListOf()

    private var priceAdapter: ArrayAdapter<String>? = null
    private var cardAdapter: ArrayAdapter<String>? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ExchangeActivity::class.java))
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_exchange

    override fun getViewModel(): BaseViewModel = muserViewModel

    override fun initView() {

        btn_back_button.setOnClickListener { onBackPressed() }

        btn_history.setOnClickListener {
            HistoryModuleActivity.start(this,4)
        }

        muserViewModel.getUserInfo(Settings.Account.id,Settings.Account.token ?: "")
            .observe(this, Observer {
                val userInfo = UserInfo(it)
                tvInfo.text = "Ví: ${userInfo?.wallet?.number} - ${balance(userInfo?.wallet?.balance_decode!!.toBigDecimal()) + " " + userInfo?.wallet?.currency_code}"

            })

        charginglistcard()
       action()

    }

    private fun charginglistcard(){
        mExchangeViewModel.charginglistcard().observe(this, Observer {
            it.map{ jsonElement -> jsonElement.asJsonObject }.map{
                val id = it.optInt("id")
                val name = it.optString("name")
                val key = it.optString("key")
                val value = it.optString("value")

                cards.add(Exchange(id, name, key, value))
                cardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,cards.map { it.name })
                spnCardName!!.adapter = cardAdapter
                cardAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            prices?.addAll(cards[0].value.split(","))
            priceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,prices)
            spnPrice!!.adapter = priceAdapter
            priceAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            cardAdapter?.notifyDataSetChanged()
            priceAdapter?.notifyDataSetChanged()

            getPriceList(cards[0].key,prices[0])

        })

    }

    private fun action(){
        spnCardName?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                prices.clear()
                prices.addAll(cards[p2].value.split(","))
                priceAdapter?.notifyDataSetChanged()

                getPriceList(cards[spnCardName.selectedItemPosition].key,
                    prices[spnPrice.selectedItemPosition])
            }


        }

        spnPrice?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getPriceList(cards[spnCardName.selectedItemPosition].key,
                    prices[spnPrice.selectedItemPosition])
            }

        }

        btn_exchange.setOnClickListener {
            edtSerial.text.toString().trim().isEmpty().yes {
                notifyError("Bạn cần nhập mã thẻ")
            }.otherwise {
                edtCardCode.text.toString().trim().isEmpty().yes {
                    notifyError("Bạn cần số seri")
                }.otherwise {
                    mExchangeViewModel.postchargingcard(cards[spnCardName.selectedItemPosition].key,
                        edtSerial.text.toString().trim(),
                        edtCardCode.text.toString().trim(),
                        prices[spnPrice.selectedItemPosition]).observe(this, Observer {
                         if(it.optInt("error_code")!=1){
                             notifyError(it.optString("message"))
                         }else{
                             edtCardCode.text?.clear()
                             edtSerial.text?.clear()
                             charginglistcard()
                             notifySuccess("Thành công!")
                         }

                    })
                }
            }

        }

    }



    private fun getPriceList(telco: String,value: String){
        mExchangeViewModel.chargingcardprice(telco,value).observe(this, Observer {
            txt_name_card.text = it.telco_key
            txt_card_value.text = balance(it.value.toInt()) + Settings.Account.currencyCode
            txt_fee.text = "${it.fees}%"
            txt_pay.text = "${balance(it.receive_amount)}${Settings.Account.currencyCode}"

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

    override fun handleError() {
        super.handleError()
    }




}