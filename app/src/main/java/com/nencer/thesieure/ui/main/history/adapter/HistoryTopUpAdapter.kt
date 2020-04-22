package com.nencer.thesieure.ui.main.history.adapter

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.service.topup.model.TopupHistoryInfo
import kotlinx.android.synthetic.main.item_history_topup.view.*
import kotlinx.android.synthetic.main.item_history_topup.view.tv_tk_thanh_toan


class HistoryTopUpAdapter(listener: OnItemClickListener<TopupHistoryInfo>): BaseAdapter<TopupHistoryInfo>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_history_topup

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<TopupHistoryInfo> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<TopupHistoryInfo>, listener: OnItemClickListener<TopupHistoryInfo>): BaseViewHolder<TopupHistoryInfo>(itemView,listItem,listener) {
        override fun bindData(t: TopupHistoryInfo, position: Int, viewType: Int) {


            itemView.tv_ma_don.text = t.order_code
            itemView.tv_status.text = t.status
            itemView.tv_thue_bao.text = t.phone_number
            itemView.tv_can_nap_value.text = "${balance(t.declared_value?: 0)} ${t.currency_code}"
            itemView.tv_ck.text = "${t.discount}%"
            itemView.tv_pay.text = "${balance(t.amount?: 0)} ${t.currency_code}"
            itemView.tv_date.text = t.created_at
            itemView.tv_da_nap_value.text = "${t.completed_value}/${balance(t.declared_value?:0)}"
            itemView.tv_note.text = t.client_note


            var tinh_trang_tt = ""
            when(t.status){
                "completed"-> {
                    tinh_trang_tt = "Hoàn thành"
                    itemView.tv_tk_thanh_toan.background = itemView.resources.getDrawable(R.drawable.background_green)
                }

                "none"-> {
                    tinh_trang_tt = "Nháp"
                    itemView.tv_tk_thanh_toan.background = itemView.resources.getDrawable(R.drawable.background_grey)

                }

                "canceled"-> {
                    tinh_trang_tt = "Đã hủy bỏ"
                    itemView.tv_tk_thanh_toan.background = itemView.resources.getDrawable(R.drawable.background_red)
                }

                "pending"-> {
                    tinh_trang_tt = "Chờ xử lý"
                    itemView.tv_tk_thanh_toan.background = itemView.resources.getDrawable(R.drawable.background_orange_box)

                }

            }
            itemView.tv_tk_thanh_toan.text = tinh_trang_tt

            var tinh_trang_don_hang = ""
            when(t.payment){
                "refunded"-> {
                    tinh_trang_don_hang = "Hoàn tiền"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_orange_box)
                }

                "none"-> {
                    tinh_trang_don_hang = "Nháp"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_grey)

                }

                "paid"-> {
                    tinh_trang_don_hang = "Đã thanh toán"
                    itemView.tv_status.background = itemView.resources.getDrawable(R.drawable.background_green)
                }

            }

            itemView.tv_status.text = tinh_trang_don_hang


        }
    }


}