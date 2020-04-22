package com.nencer.thesieure.ui.main.history.adapter.wallet

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.service.wallet.response.HistoryInfo
import kotlinx.android.synthetic.main.item_history_all.view.*


class HistoryAllAdapter(listener: OnItemClickListener<HistoryInfo>): BaseAdapter<HistoryInfo>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_history_all

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<HistoryInfo> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<HistoryInfo>, listener: OnItemClickListener<HistoryInfo>): BaseViewHolder<HistoryInfo>(itemView,listItem,listener) {
        override fun bindData(t: HistoryInfo, position: Int, viewType: Int) {
            itemView.tv_ma_giao_dich.text = t.order_code
            itemView.tv_truoc_giao_dich.text = balance(t.my_before_balance)
            itemView.tv_sau_giao_dich.text = balance(t.my_after_balance)
            itemView.tv_so_tien.text = t.my_pay_amount
            itemView.tv_tien_te.text = t.currency_code
            itemView.tv_ngay_tao.text = t.created_at
            itemView.tv_ghi_chu.text = t.description

            t.my_pay_amount?.let {
                if(it.contains("-")){
                    itemView.tv_so_tien.setTextColor(itemView.resources.getColor(R.color.red_1))
                }else itemView.tv_so_tien.setTextColor(itemView.resources.getColor(R.color.green_1))
            }




        }
    }


}