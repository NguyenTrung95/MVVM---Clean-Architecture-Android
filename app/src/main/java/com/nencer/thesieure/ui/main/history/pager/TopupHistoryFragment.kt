package com.nencer.thesieure.ui.main.history.pager

import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.base.fragment.BaseVMFragment
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.customview.CustomLoadingListItemCreator
import com.nencer.thesieure.ext.gone
import com.nencer.thesieure.ext.visible
import com.nencer.thesieure.service.payment.PayViewModel
import com.nencer.thesieure.service.topup.TopupViewModel
import com.nencer.thesieure.service.topup.model.TopupHistoryInfo
import com.nencer.thesieure.ui.main.history.adapter.HistoryTopUpAdapter
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TopupHistoryFragment : BaseVMFragment(), Paginate.Callbacks {


    private val mViewModel: PayViewModel by viewModel()
    private var page = 1
    private var loading: Boolean = false
    private var hasLoadedAllItems = false

    private lateinit var adapter: HistoryTopUpAdapter
    private val mTopupModel: TopupViewModel by viewModel()
    private val listHistoryTopup: MutableList<TopupHistoryInfo> = mutableListOf()

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_pager

    override fun initData() {
        loading = false

        adapter =
            HistoryTopUpAdapter(
                object :
                    OnItemClickListener<TopupHistoryInfo> {
                    override fun onClick(item: TopupHistoryInfo, position: Int) {
                    }
                })
        rcyPager.adapter = adapter
        loadTopupHistory(false)

        Paginate.with(rcyPager, this)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(CustomLoadingListItemCreator())
            .build()
    }
    override fun initView() {



        swr_pager.setOnRefreshListener {
            listHistoryTopup.clear()
            loadTopupHistory(false)
        }

    }


    private fun loadTopupHistory(loadMore: Boolean){
        if (loading) {
            return
        }

        loading = true

        if (!loadMore) {
            page = 1
        }
        mTopupModel.mtopuphistory(page).observe(this, Observer {
            loading = false
            swr_pager.isRefreshing = false
            page += 1
            hasLoadedAllItems = it.items.size < 10
            listHistoryTopup.addAll(it.items)
            adapter.setListItems(listHistoryTopup)
            if(listHistoryTopup.size == 0) {
                tv_status.visible()
                rcyPager.gone()
            }
            else {
                tv_status.gone()
                rcyPager.visible()
            }
        })
    }

    override fun onLoadMore() {
        loadTopupHistory(true)

    }

    override fun isLoading(): Boolean = loading

    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems

    override fun onResume() {
        super.onResume()
//        loadTopupHistory(false)
    }

}