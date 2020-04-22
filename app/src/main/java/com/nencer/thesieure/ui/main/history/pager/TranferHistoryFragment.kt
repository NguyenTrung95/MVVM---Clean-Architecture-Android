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
import com.nencer.thesieure.service.wallet.WalletViewModel
import com.nencer.thesieure.service.wallet.response.HistoryInfo
import com.nencer.thesieure.ui.main.history.adapter.wallet.HistoryTransferAdapter
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TranferHistoryFragment : BaseVMFragment(), Paginate.Callbacks{


    private val mViewModel: PayViewModel by viewModel()
    private var page = 1
    private var loading: Boolean = false
    private var hasLoadedAllItems = false

    private lateinit var adapterTransfer: HistoryTransferAdapter

    private val listHistoryCommon: MutableList<HistoryInfo> = mutableListOf()

    private val mWalletDepositModel: WalletViewModel by viewModel()


    override fun getViewModel(): BaseViewModel = mViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_pager

    override fun initData() {
        loading = false

        adapterTransfer =
            HistoryTransferAdapter(
                object :
                    OnItemClickListener<HistoryInfo> {
                    override fun onClick(item: HistoryInfo, position: Int) {
                    }
                })
        rcyPager.adapter = adapterTransfer

        loadDataHistoryWallet(false)

        Paginate.with(rcyPager, this)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(CustomLoadingListItemCreator())
            .build()
    }

    override fun initView() {


        swr_pager.setOnRefreshListener {
            listHistoryCommon.clear()
            loadDataHistoryWallet(false)
        }


    }

    override fun onResume() {
        super.onResume()
//        loadDataHistoryWallet(false)
    }


    private fun loadDataHistoryWallet(loadMore: Boolean) {
        if (loading) {
            return
        }

        loading = true

        if (!loadMore) {
            page = 1
        }


                mWalletDepositModel.history("Transfer", page).observe(
                    this,
                    Observer { historyResponse ->
                        loading = false
                        swr_pager.isRefreshing = false
                        hasLoadedAllItems = historyResponse.data.size < 10
                        listHistoryCommon.addAll(historyResponse.data)
                        adapterTransfer.setListItems(listHistoryCommon)
                        page += 1
                        if(listHistoryCommon.size == 0) {
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
        loadDataHistoryWallet(true)


    }

    override fun isLoading(): Boolean = loading

    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems

}