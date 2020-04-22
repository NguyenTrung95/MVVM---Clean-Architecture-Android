package com.nencer.thesieure.ui.main.home.adapter

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.ext.balance
import com.nencer.thesieure.service.payment.response.Card
import kotlinx.android.synthetic.main.item_my_order.view.*
import java.math.BigDecimal

class OrderAdapter(private val minusListener: (card: Card, position: Int) -> Unit,
                   private val plusListener: (card: Card, position: Int) -> Unit ): BaseAdapter<Card>() {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_my_order

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<Card> {
        return HomeViewHolder(view,listItems)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<Card>): BaseViewHolder<Card>(itemView,listItem) {
        override fun bindData(t: Card, position: Int, viewType: Int) {
            itemView.tv_name_card.text = t.name
            itemView.tv_count.text = t.count.toString()
            itemView.tv_price.text = balance(t.price?: BigDecimal.ZERO) +"đ"

            itemView.imv_minus.setOnClickListener { minusListener.invoke(t,position) }
            itemView.imv_plus.setOnClickListener { plusListener.invoke(t,position) }
        }
    }


}