package com.nencer.thesieure.ui.main.home.adapter

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.ui.main.home.model.CardInfo
import kotlinx.android.synthetic.main.item_oder.view.*

class OrderDetailAdapter(private val  codeCopy:(value: String)->Unit
                         ,private val  serialCopy:(value: String)->Unit): BaseAdapter<CardInfo>() {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_oder

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<CardInfo> {
        return HomeViewHolder(view,listItems)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<CardInfo>): BaseViewHolder<CardInfo>(itemView,listItem) {
        override fun bindData(t: CardInfo, position: Int, viewType: Int) {
            itemView.tv_code.text ="Mã code: ${t.code}"
            itemView.tv_seri.text = "Seri: ${t.serial}"
            itemView.tv_count_name_card.text = t.name

            itemView.btn_copy_code.setOnClickListener { codeCopy.invoke(t.code.toString()) }
            itemView.btn_copy_seri.setOnClickListener { serialCopy.invoke(t.serial.toString()) }

        }
    }


}