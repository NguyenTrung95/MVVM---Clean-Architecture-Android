package com.nencer.thesieure.ui.main.history

import android.content.Context
import android.content.Intent
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseActivity
import com.nencer.thesieure.helper.ClipboardUtil
import com.nencer.thesieure.helper.ToastUtil
import com.nencer.thesieure.ui.main.home.adapter.OrderDetailAdapter
import com.nencer.thesieure.ui.main.home.model.OrderData
import kotlinx.android.synthetic.main.activity_history_detail.*
import kotlinx.android.synthetic.main.content_navitation.*

class HistoryDetailActivity : BaseActivity() {
    private var adapter: OrderDetailAdapter? = null

    companion object {
        private val TAG = HistoryDetailActivity::class.java.name
        fun start(context: Context, orderData: OrderData) {
            val start = Intent(context, HistoryDetailActivity::class.java)
            start.putExtra(TAG, orderData)
            context.startActivity(start)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_history_detail

    override fun initView() {
        tv_title.text = "Chi tiết"

        val data = intent?.getParcelableExtra<OrderData>(
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
            adapter!!.setListItems(data.listCardInfo)
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


    }


}