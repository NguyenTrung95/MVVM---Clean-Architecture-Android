package com.nencer.thesieure.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nencer.thesieure.R
import com.nencer.thesieure.service.wallet.response.BankUser
import kotlinx.android.synthetic.main.item_bank.view.*


class SimpleAdapter(private val items: MutableList<BankUser>) : RecyclerView.Adapter<SimpleAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun addItem(name: BankUser) {
        items.add(name)
        notifyItemInserted(items.size)
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int) : BankUser{
        return items.get(position)
    }

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)) {

        fun bind(t: BankUser) = with(itemView) {
            itemView.tv_ngan_hang.text =t.bankname
            itemView.tv_chi_nhanh.text = t.branch
            itemView.tv_stk.text = t.acc_num
            itemView.tv_chu_tai_khoan.text = t.acc_name
            itemView.tv_so_atm.text = t.card_num
            if(t.approved == "0"){
                itemView.tv_trang_thai.text = "Chưa duyệt"
                itemView.tv_trang_thai.setBackgroundColor(resources.getColor(R.color.orange_1))
            }else{
                itemView.tv_trang_thai.text = "Đã duyệt"
                itemView.tv_trang_thai.setBackgroundColor(resources.getColor(R.color.green_1))

            }
        }
    }
}