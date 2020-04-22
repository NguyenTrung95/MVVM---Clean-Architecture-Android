package com.nencer.thesieure.ui.main.history.adapter

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.service.exchange.response.HistoryChargingInfo
import kotlinx.android.synthetic.main.item_history_charging.view.*
import kotlinx.android.synthetic.main.item_history_charging.view.tv_don_hang
import kotlinx.android.synthetic.main.item_history_charging.view.tv_ma_giao_dich
import kotlinx.android.synthetic.main.item_history_charging.view.tv_money
import kotlinx.android.synthetic.main.item_history_charging.view.tv_ngay_tao
import kotlinx.android.synthetic.main.item_history_charging.view.tv_status
import kotlinx.android.synthetic.main.item_history_charging.view.tv_user_send
import kotlinx.android.synthetic.main.item_history_charging.view.tvfee


class HistoryExchangeAdapter(listener: OnItemClickListener<HistoryChargingInfo>): BaseAdapter<HistoryChargingInfo>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_history_charging

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<HistoryChargingInfo> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<HistoryChargingInfo>, listener: OnItemClickListener<HistoryChargingInfo>): BaseViewHolder<HistoryChargingInfo>(itemView,listItem,listener) {
        override fun bindData(t: HistoryChargingInfo, position: Int, viewType: Int) {


            itemView.tv_ma_giao_dich.text = t.code
            itemView.tv_money.text = t.serial
            itemView.tvfee.text = t.telco
            itemView.tv_don_hang.text = "${balance(t.declared_value?: 0)} ${t.currency_code}"
            itemView.tv_user_send.text = "${balance(t.real_value?: 0)} ${t.currency_code}"
            itemView.tv_ngay_tao.text = t.fees.toString() + "%"
            itemView.tv_phat.text = t.penalty.toString()
            itemView.tv_ngay_tao_moi.text = t.created_at
            itemView.tv_nhan_value.text = balance(t.amount) + t.currency_code
            itemView.tv_status.text = t.status

            var tinh_trang_tt = ""
            when(t.description.trim()){
                "Thành công".trim()-> {
                    tinh_trang_tt = "Thành công"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_green)
                }

                "".trim()-> {
                    tinh_trang_tt = "Nháp"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_grey)

                }

                "Chờ xử lý".trim()-> {
                    tinh_trang_tt = "Chờ xử lý"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_orange_box)

                }

            }

            itemView.tv_status.text =   tinh_trang_tt


        }
    }


}