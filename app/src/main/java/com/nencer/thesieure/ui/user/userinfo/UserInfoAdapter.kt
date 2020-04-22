package com.nencer.thesieure.ui.user.userinfo

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_editer.view.tv_title
import kotlinx.android.synthetic.main.item_user_info.view.*

class UserInfoAdapter: BaseAdapter<Pair<String,String>>() {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_user_info

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<Pair<String,String>> {
        return HomeViewHolder(view,listItems)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<Pair<String,String>>): BaseViewHolder<Pair<String,String>>(itemView,listItem) {
        override fun bindData(t: Pair<String,String>, position: Int, viewType: Int) {
            itemView.tv_title.text =t.first
            itemView.tv_value.setText(t.second)
        }
    }


}