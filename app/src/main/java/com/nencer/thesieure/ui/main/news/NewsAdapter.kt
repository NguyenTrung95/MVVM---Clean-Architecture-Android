package com.nencer.thesieure.ui.main.news

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.di.BASE_URL
import com.nencer.thesieure.service.info.model.NewsData
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(listener: OnItemClickListener<NewsData>): BaseAdapter<NewsData>(listener) {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_news

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<NewsData> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<NewsData>, listener: OnItemClickListener<NewsData>): BaseViewHolder<NewsData>(itemView,listItem,listener) {
        override fun bindData(t: NewsData, position: Int, viewType: Int) {

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(
                    RequestOptions()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading))
                .load("${BASE_URL}${t?.image?.trim()}")
                .into(itemView.imvThumbnail)
            itemView.tvTitle.text = t?.title
            itemView.tvDate.text = t?.created_at
            itemView.tvView.text = "Lượt xem: ${t?.view_count}"

        }
    }


}