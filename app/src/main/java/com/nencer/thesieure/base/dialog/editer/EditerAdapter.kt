package com.nencer.thesieure.base.dialog.editer

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_editer.view.*

class EditerAdapter: BaseAdapter<Triple<String,String,Boolean>>() {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_editer

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<Triple<String,String,Boolean>> {
        return HomeViewHolder(view,listItems)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<Triple<String,String,Boolean>>): BaseViewHolder<Triple<String,String,Boolean>>(itemView,listItem) {
        override fun bindData(t: Triple<String,String,Boolean>, position: Int, viewType: Int) {
            itemView.tv_title.text =t.first
            if (t.third) itemView.edt_value.hint = t.second
            else itemView.edt_value.setText(t.second)
        }
    }


}