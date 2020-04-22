package com.nencer.thesieure.ui.main.history

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.nencer.thesieure.R
import com.nencer.thesieure.base.fragment.BaseVMFragment
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.service.payment.PayViewModel
import com.nencer.thesieure.ui.main.history.pager.*
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : BaseVMFragment() {


    private val mViewModel: PayViewModel by viewModel()
    override fun getViewModel(): BaseViewModel = mViewModel
    override fun getLayoutRes(): Int = R.layout.fragment_history


    override fun initView() {
        val adapter = FragmentPagerItemAdapter(
            fragmentManager, FragmentPagerItems.with(context!!)
                .add("LỊCH SỬ THAY ĐỔI SỐ DƯ", AllHistoryFragment::class.java)
                .add("Lịch sử mua thẻ", PaymentHistoryFragment::class.java)
                .add("Lịch sử chuyển tiền", TranferHistoryFragment::class.java)
                .add("Lịch sử nạp tiền", DepositHistoryFragment::class.java)
                .add("Lịch sử rút tiền", WithdrawHistoryFragment::class.java)
                .add("Lịch sử đổi thẻ", ExchangeHistoryFragment::class.java)
                .add("Lịch sử nạp topup", TopupHistoryFragment::class.java)
                .create()
        )
        val  items = FragmentPagerItems.with(context)

        viewpager.adapter = adapter

        viewpagertab.setViewPager(viewpager)




        viewpagertab.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


            }

            override fun onPageSelected(position: Int) {
                val count = adapter.count
                for (i in 0 until count) {

                    var fragment : Fragment = adapter.getPage(position)

                    val view = viewpagertab.getTabAt(i) as TextView
                    view.setTextColor(resources.getColor(R.color.black_1))
                }
                val view = viewpagertab.getTabAt(position) as TextView
                view.setTextColor(resources.getColor(R.color.blue_1))
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        val view = viewpagertab.getTabAt(0) as TextView
        view.setTextColor(resources.getColor(R.color.blue_1))

    }


}