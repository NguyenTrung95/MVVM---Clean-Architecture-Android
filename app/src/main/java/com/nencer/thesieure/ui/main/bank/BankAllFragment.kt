package com.nencer.thesieure.ui.main.bank


import android.app.Activity
import android.content.Intent
import android.util.Log

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.SimpleAdapter
import com.nencer.thesieure.base.fragment.BaseVMFragment
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.ext.gone
import com.nencer.thesieure.ext.optString
import com.nencer.thesieure.ext.visible
import com.nencer.thesieure.service.wallet.WalletViewModel
import com.nencer.thesieure.service.wallet.response.BankUser
import com.nencer.thesieure.ui.wallet.InsertAccountBankActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.kitek.rvswipetodelete.SwipeToDeleteCallback


class BankAllFragment : BaseVMFragment() {
    private val walletViewModel : WalletViewModel by viewModel()
    override fun getViewModel(): BaseViewModel = walletViewModel
    private val values: MutableList<BankUser> = mutableListOf()
    override fun getLayoutRes(): Int = R.layout.fragment_bank
    private var adapter: SimpleAdapter?=null


    override fun initData() {
        values.clear()
        loadData()
    }
    override fun initView() {



        swr_pager.setOnRefreshListener {
            values.clear()
            loadData()}

        activity!!.btn_add.setOnClickListener{
            startActivityForResult(Intent(activity!!, InsertAccountBankActivity::class.java), 101)

        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("test", "test")
//        loadData()
    }

    private fun loadData() {
        walletViewModel.getListBankUser("all").observe(this, Observer {
            swr_pager.isRefreshing = false
            values.addAll(it.datas)
            if (it.datas.size == 0) {
                tv_status.visible()
                tv_status.text = "Không có dữ liệu"
            }else tv_status.gone()
            adapter = SimpleAdapter(values)

            rcyPager.adapter = adapter
//            rcyPager.addItemDecoration( DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//            adapter?.setListItems(it.datas)

            val swipeHandler = object : SwipeToDeleteCallback(activity!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = rcyPager.adapter as SimpleAdapter
                    var item = adapter.getItem(viewHolder.adapterPosition)
                    walletViewModel.deletebankuser(item.id.toString()).observe(activity!!,
                        Observer {
                            val code = it.optString("error_code")
                            val msg = it.optString("message")
                            if("0".equals(code)){
                                adapter.removeAt(viewHolder.adapterPosition)
                            }else{
                                if (msg.isNotEmpty()) {
//                                    notifyError(msg)
                                }
                            }
                        })

                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(rcyPager)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            walletViewModel.getListBankUser("all").observe(this, Observer {
                adapter = SimpleAdapter(it.datas)
                rcyPager.adapter = adapter
            })
        }
    }

    override fun handleError() {
        tv_status.visible()
    }



}