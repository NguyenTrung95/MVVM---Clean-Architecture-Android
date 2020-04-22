package com.nencer.thesieure.ui.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseActivity
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.helper.ClipboardUtil
import com.nencer.thesieure.helper.ToastUtil
import com.nencer.thesieure.ui.main.home.adapter.OrderDetailAdapter
import com.nencer.thesieure.ui.main.home.model.OrderData
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.content_navitation.*

class PaymentDetailActivity : BaseActivity() {
    private var adapter: OrderDetailAdapter? = null

    companion object {
        private val TAG  = PaymentDetailActivity::class.java.name
        fun start(context: Activity,orderData: OrderData) {
            val start = Intent(context, PaymentDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(TAG, orderData);
            start.putExtras(bundle)
            context.startActivityForResult(start,1989)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_order_detail

    override fun initView() {
        tv_title.text = "Thông tin thanh toán"

        val data = intent?.extras?.getParcelable<OrderData>(
            TAG
        )

        adapter = OrderDetailAdapter(codeCopy = {
            ClipboardUtil.copyToClipBoard(this, "Mã nạp: ", it)
            ToastUtil.show("Đã copy mã nạp")
        }, serialCopy = {
            ClipboardUtil.copyToClipBoard(this, "Seri: ", it)
            ToastUtil.show("Đã copy seri")
        })

        rcyOrderDetail.adapter = adapter

        data?.let {
            tvCodePackage.text = "Đơn hàng: ${data.order_code}"
            adapter!!.setListItems(data.listCardInfo)
            tv_status_value.text = when(data.status){
                "completed" -> "Hoàn Thành"
                else -> "Thất bại"
            }
            tvValue.text = "${data.pay_amount?.toBigDecimal()?.let { it1 -> balance(it1) }} ${data.currency_code}"
        }

        btn_back_button.setOnClickListener { onBackPressed() }
        btn_copy_all.setOnClickListener {
            val data = StringBuffer()
            adapter?.listItems?.forEach {
                data.append(it.name)
                data.append("\n")
                data.append("code: ${it.code}")
                data.append("\n")
                data.append("seri: ${it.serial}")
                data.append("\n")
            }
            ClipboardUtil.copyToClipBoard(this, "All: ", data.toString())
            ToastUtil.show("Đã copy tất cả thông tin")
        }

        btn_next_buy.setOnClickListener { onBackPressed() }


    }


}