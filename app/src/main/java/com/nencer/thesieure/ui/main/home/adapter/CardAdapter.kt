package com.nencer.thesieure.ui.main.home.adapter

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.di.BASE_URL
import com.nencer.thesieure.ext.GlideApp
import com.nencer.thesieure.ext.placeholder
import com.nencer.thesieure.ui.main.home.model.Item
import kotlinx.android.synthetic.main.item_pay.view.*

class CardAdapter(listener: OnItemClickListener<Item>): BaseAdapter<Item>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_pay

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<Item> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<Item>, listener: OnItemClickListener<Item>): BaseViewHolder<Item>(itemView,listItem,listener) {
        override fun bindData(t: Item, position: Int, viewType: Int) {

            GlideApp.with(itemView.context).placeholder(R.drawable.loading)
                .load("${BASE_URL}${t.image?.trim()}")
                .into( itemView.imvLogo)

            if (t.isSeleted) itemView.ll_bg.background = itemView.resources.getDrawable(R.drawable.bg_border_selected)
            else itemView.ll_bg.background = itemView.resources.getDrawable(R.drawable.bg_border_black)
        }
    }


}