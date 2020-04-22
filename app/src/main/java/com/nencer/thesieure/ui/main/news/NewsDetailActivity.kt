package com.nencer.thesieure.ui.main.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseVMActivity
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.service.info.InfoViewModel
import kotlinx.android.synthetic.main.activity_news_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailActivity : BaseVMActivity() {

    private val mViewModel: InfoViewModel by viewModel()

    companion object{
        private val TAG  = NewsDetailActivity::class.java.name
        fun start(context: Context,id: Int) {
            val start = Intent(context, NewsDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(TAG, id)
            start.putExtras(bundle)
            context.startActivity(start)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_news_detail

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun initView() {
        val id = intent?.extras?.getInt(TAG)


        btn_back_button.setOnClickListener { onBackPressed() }

        mViewModel.getnewsDetail(id?:0).observe(this, Observer {
            tv_title.text = it.title
            tv_title_news.text = it.title
            tv_author.text = "Tác giả: " + it.author
            wv_content.loadDataWithBaseURL(null, it.content, "text/html", "utf-8", null)
            tv_create_at.text ="Ngày tạo: " + it.created_at
            tv_email.text ="Liên hệ: " + it.author_email


        })

    }
    
}