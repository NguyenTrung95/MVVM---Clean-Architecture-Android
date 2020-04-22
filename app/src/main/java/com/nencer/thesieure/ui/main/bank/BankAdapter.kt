package com.nencer.thesieure.ui.main.bank

import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.BaseAdapter
import com.nencer.thesieure.base.adapter.BaseViewHolder
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.service.wallet.response.BankUser
import kotlinx.android.synthetic.main.item_bank.view.*

class BankAdapter(listener: OnItemClickListener<BankUser>): BaseAdapter<BankUser>(listener) {

    override fun getLayoutId(viewType: Int): Int = R.layout.item_bank

    override fun createViewHolder(view: View, viewType: Int): BaseViewHolder<BankUser> {
        return HomeViewHolder(view,listItems, mOnItemClickListener!!)
    }

    inner class HomeViewHolder(itemView: View, listItem: MutableList<BankUser>, listener: OnItemClickListener<BankUser>): BaseViewHolder<BankUser>(itemView,listItem,listener) {
        override fun bindData(t: BankUser, position: Int, viewType: Int) {
            itemView.tv_ngan_hang.text =t.bankname
            itemView.tv_chi_nhanh.text = t.branch
            itemView.tv_stk.text = t.acc_num
            itemView.tv_chu_tai_khoan.text = t.acc_name
            itemView.tv_so_atm.text = t.card_num
            itemView.tv_trang_thai.text = t.approved
        }
    }


}