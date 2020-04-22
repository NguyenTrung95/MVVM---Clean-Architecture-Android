package com.nencer.thesieure.ui.main.sercurity

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.service.user.model.response.OTP
import kotlinx.android.synthetic.main.item_otp.view.*

class OTPAdapter(listener: OnItemClickListener<OTP>): BaseAdapter<OTP>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_otp

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<OTP> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<OTP>, listener: OnItemClickListener<OTP>): BaseViewHolder<OTP>(itemView,listItem,listener) {
        override fun bindData(t: OTP, position: Int, viewType: Int) {
            itemView.tv_name_otp.text =t.type
            itemView.tv_msg.text = t.text
            itemView.tv_date.text = t.expired_at

        }
    }


}