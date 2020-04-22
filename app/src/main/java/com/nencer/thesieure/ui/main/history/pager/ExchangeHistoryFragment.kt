package com.nencer.thesieure.ui.main.history.pager
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.base.fragment.BaseVMFragment
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.customview.CustomLoadingListItemCreator
import com.nencer.thesieure.ext.gone
import com.nencer.thesieure.ext.visible
import com.nencer.thesieure.service.exchange.ExchangeViewModel
import com.nencer.thesieure.service.exchange.response.HistoryChargingInfo
import com.nencer.thesieure.service.payment.PayViewModel
import com.nencer.thesieure.ui.main.history.adapter.HistoryExchangeAdapter
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExchangeHistoryFragment : BaseVMFragment(), Paginate.Callbacks{


    private val mViewModel: PayViewModel by viewModel()
    private var page = 1
    private var loading: Boolean = false
    private var hasLoadedAllItems = false

    private lateinit var adapterExchange: HistoryExchangeAdapter
    private val listExchangeHistory: MutableList<HistoryChargingInfo> = mutableListOf()
    private val mExchangeModel: ExchangeViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_pager

    override fun initData() {
        loading = false

        adapterExchange =
            HistoryExchangeAdapter(
                object :
                    OnItemClickListener<HistoryChargingInfo> {
                    override fun onClick(item: HistoryChargingInfo, position: Int) {
                    }
                })
        rcyPager.adapter = adapterExchange

        loadDataExchange(false)

        Paginate.with(rcyPager, this)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(CustomLoadingListItemCreator())
            .build()
    }
    override fun initView() {


        swr_pager.setOnRefreshListener {
            listExchangeHistory.clear()
            loadDataExchange(false)
        }


    }

    private fun loadDataExchange(loadMore: Boolean) {
        if (loading) {
            return
        }

        loading = true

        if (!loadMore) {
            page = 1
        }
        mExchangeModel.charginghistory(10, page).observe(this, Observer { historyResponse ->
            loading = false
            swr_pager.isRefreshing = false
            hasLoadedAllItems = historyResponse.data.size < 10
            listExchangeHistory.addAll(historyResponse.data)
            adapterExchange.setListItems(listExchangeHistory)
            page += 1

            if(listExchangeHistory.size == 0) {
                tv_status.visible()
                rcyPager.gone()
            }
            else {
                tv_status.gone()
                rcyPager.visible()
            }


        })
    }

    override fun onResume() {
        super.onResume()
//        loadDataExchange(false)
    }


    override fun onLoadMore() {
     loadDataExchange(true)
    }

    override fun isLoading(): Boolean = loading

    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems

}