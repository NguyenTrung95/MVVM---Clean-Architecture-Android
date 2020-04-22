package com.nencer.thesieure.ui.main.history.adapter.wallet

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.service.wallet.response.HistoryInfo
import kotlinx.android.synthetic.main.item_history_transfer.view.*



class HistoryTransferAdapter(listener: OnItemClickListener<HistoryInfo>): BaseAdapter<HistoryInfo>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_history_transfer

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<HistoryInfo> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<HistoryInfo>, listener: OnItemClickListener<HistoryInfo>): BaseViewHolder<HistoryInfo>(itemView,listItem,listener) {
        override fun bindData(t: HistoryInfo, position: Int, viewType: Int) {
            itemView.tv_ma_giao_dich.text = t.order_code
            itemView.tv_money.text = "${balance(t.pay_amount?: 0)} ${t.currency_code}"
            itemView.tvfee.text = "${balance(t.fees?: 0)} ${t.currency_code}"
            itemView.tv_don_hang.text = "${balance(t.net_amount?: 0)} ${t.currency_code}"
            itemView.tv_user_send.text = t?.clientname?.name
            itemView.tv_ngay_tao.text = t.created_at
            itemView.txtDes.text = t.description
            var tinh_trang_tt = ""
            when(t.status){
                "completed"-> {
                    tinh_trang_tt = "Hoàn thành"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_green)
                }

                "none"-> {
                    tinh_trang_tt = "Nháp"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_grey)

                }

                "canceled"-> {
                    tinh_trang_tt = "Đã hủy bỏ"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_red)
                }

                "pending"-> {
                    tinh_trang_tt = "Chờ xử lý"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_orange_box)

                }

            }
            itemView.tv_status.text = tinh_trang_tt


        }
    }


}