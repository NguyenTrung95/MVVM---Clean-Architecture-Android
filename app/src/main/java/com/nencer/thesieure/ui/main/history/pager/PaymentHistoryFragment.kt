package com.nencer.thesieure.ui.main.history.pager
import android.util.Log
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.base.fragment.BaseVMFragment
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.customview.CustomLoadingListItemCreator
import com.nencer.thesieure.ext.gone
import com.nencer.thesieure.ext.visible
import com.nencer.thesieure.service.payment.PayViewModel
import com.nencer.thesieure.service.payment.response.HistoryOrder
import com.nencer.thesieure.ui.main.history.adapter.HistoryPaymentAdapter
import com.nencer.thesieure.ui.main.history.HistoryDetailActivity
import com.nencer.thesieure.ui.main.home.model.CardInfo
import com.nencer.thesieure.ui.main.home.model.OrderData

import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PaymentHistoryFragment : BaseVMFragment(), Paginate.Callbacks{


    private val mViewModel: PayViewModel by viewModel()
    private var page = 1
    private var loading: Boolean = false
    private var hasLoadedAllItems = false

    private  var paymentAdapter: HistoryPaymentAdapter?=null

    private val listPaymentHistory: MutableList<HistoryOrder> = mutableListOf()

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_pager

    override fun initData() {
        loading = false

        paymentAdapter =
            HistoryPaymentAdapter(
                object :
                    OnItemClickListener<HistoryOrder> {
                    override fun onClick(item: HistoryOrder, position: Int) {
                        val order = OrderData()
                        order.currency_code = item.currency_code
                        order.pay_amount = item.pay_amount.toString()
                        order.status = item.status
                        order.order_code = item.order_code
                        item.cardinfo.forEach {
                            val cardInfo = CardInfo()
                            cardInfo.code = it.code
                            cardInfo.serial = it.serial
                            cardInfo.created_at = it.created_at
                            cardInfo.name = it.name
                            cardInfo.user_id = it.user_id.toString()
                            cardInfo.updated_at = it.updated_at
                            order.listCardInfo.add(cardInfo)
                        }

                        HistoryDetailActivity.start(
                            context!!,
                            order
                        )
                    }

                })
        rcyPager.adapter = paymentAdapter

        loadPaymentHistory(false)
        loading = false
        Paginate.with(rcyPager, this)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(CustomLoadingListItemCreator())
            .build()
    }
    override fun initView() {


        swr_pager.setOnRefreshListener {
            listPaymentHistory.clear()
            loadPaymentHistory(false)
        }


    }

    private fun loadPaymentHistory(loadMore: Boolean) {
        if (loading) {
            return
        }

        loading = true

        if (!loadMore) {
            page = 1
        }

        mViewModel.paymentHistory(Settings.Account.id, Settings.Account.token, page)
            .observe(this, Observer { historyResponse ->
                loading = false
                swr_pager.isRefreshing = false
                hasLoadedAllItems = historyResponse.data.size < 10

                listPaymentHistory.addAll(historyResponse.data)
                paymentAdapter?.setListItems(listPaymentHistory)
                page += 1
                if(listPaymentHistory.size == 0) {
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
//        loadPaymentHistory(false)
    }

    override fun onLoadMore() {
        Log.d("vao day ", "vao day");
       loadPaymentHistory(true)
    }

    override fun isLoading(): Boolean = loading

    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems

}