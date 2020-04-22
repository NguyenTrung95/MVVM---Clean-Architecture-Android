package com.nencer.thesieure.ui.main.history

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentTransaction
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseActivity
import com.nencer.thesieure.ext.visible
import com.nencer.thesieure.ui.main.history.pager.*
import com.nencer.thesieure.ui.wallet.TranferActivity
import kotlinx.android.synthetic.main.activity_history.*


class HistoryModuleActivity : BaseActivity() {

    companion object {
        val TAG = HistoryModuleActivity::class.java.name
        fun start(context: Context,code:Int?=0, isNext: Boolean = false) {
            val start = Intent(context, HistoryModuleActivity::class.java)
            start.putExtra(TAG,code)
            start.putExtra("NEXT_BUY",isNext)
            context.startActivity(start)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_history

    override fun initView() {
        val code = intent.getIntExtra(TAG,0)
        val isNext = intent.getBooleanExtra("NEXT_BUY",false)

        if (isNext){
            btn_next_buy.visible()
            btn_next_buy.setOnClickListener {
                onBackPressed()
            }
        }
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when(code){
            1 -> {
                fragmentTransaction.add(R.id.fl_container, TranferHistoryFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            2 -> {
                fragmentTransaction.add(R.id.fl_container, WithdrawHistoryFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            3 -> {
                fragmentTransaction.add(R.id.fl_container, DepositHistoryFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            4 -> {
                fragmentTransaction.add(R.id.fl_container, ExchangeHistoryFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            5 -> {
                fragmentTransaction.add(R.id.fl_container, TopupHistoryFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }


        btn_back_button.setOnClickListener { finish() }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}