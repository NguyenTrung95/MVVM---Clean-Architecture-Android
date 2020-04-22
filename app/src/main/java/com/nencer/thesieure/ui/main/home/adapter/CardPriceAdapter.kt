package com.nencer.thesieure.ui.main.home.adapter

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.service.payment.response.Card
import kotlinx.android.synthetic.main.item_card.view.*

class CardPriceAdapter(listener: OnItemClickListener<Card>): BaseAdapter<Card>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_card

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<Card> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<Card>, listener: OnItemClickListener<Card>): BaseViewHolder<Card>(itemView,listItem,listener) {
        override fun bindData(t: Card, position: Int, viewType: Int) {
            itemView.txtCard.text =t.name
            if (t.isOrder) {
                itemView.ll_bg.background = itemView.resources.getDrawable(R.drawable.bg_item_card_selected)
                itemView.txtCard.setTextColor(itemView.resources.getColor(R.color.white_1))
            } else {
                itemView.ll_bg.background = itemView.resources.getDrawable(R.drawable.bg_item_card)
                itemView.txtCard.setTextColor(itemView.resources.getColor(R.color.black_1))
            }
        }
    }


}