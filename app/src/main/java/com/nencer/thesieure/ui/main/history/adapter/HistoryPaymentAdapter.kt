package com.nencer.thesieure.ui.main.history.adapter

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.service.payment.response.HistoryOrder
import kotlinx.android.synthetic.main.item_history.view.*
import kotlinx.android.synthetic.main.item_history.view.tv_tk_thanh_toan

class HistoryPaymentAdapter(listener: OnItemClickListener<HistoryOrder>): BaseAdapter<HistoryOrder>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_history

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<HistoryOrder> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<HistoryOrder>, listener: OnItemClickListener<HistoryOrder>): BaseViewHolder<HistoryOrder>(itemView,listItem,listener) {
        override fun bindData(t: HistoryOrder, position: Int, viewType: Int) {
            itemView.tv_ma_giao_dich.text = t.order_code
            var san_pham = ""
            t.cardinfo.forEach {
                san_pham += it.name +"  "
            }
            itemView.tv_san_pham.text = san_pham

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
                    itemView.tv_tk_thanh_toan.background = itemView.resources.getDrawable(R.drawable.background_orange_box)
                }

                "none"-> {
                    tinh_trang_don_hang = "Nháp"
                    itemView.tv_tk_thanh_toan.background = itemView.resources.getDrawable(R.drawable.background_grey)

                }

                "paid"-> {
                    tinh_trang_don_hang = "Đã thanh toán"
                    itemView.tv_tk_thanh_toan.background = itemView.resources.getDrawable(R.drawable.background_green)
                }

            }

            itemView.tv_don_hang.text = tinh_trang_don_hang

            itemView.tv_so_tien.text = "${balance(t.pay_amount?: 0)} ${t.currency_code}"
            itemView.tv_ngay_tao.text = if (t.cardinfo.isNotEmpty()) t.cardinfo[0].created_at else ""

        }
    }


}