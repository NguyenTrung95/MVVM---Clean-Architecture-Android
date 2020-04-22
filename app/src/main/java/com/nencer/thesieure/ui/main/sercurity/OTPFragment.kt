package com.nencer.thesieure.ui.main.sercurity

import android.util.Log
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.base.fragment.BaseVMFragment
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.ext.gone
import com.nencer.thesieure.ext.visible
import com.nencer.thesieure.service.user.model.response.OTP
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_sercurity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OTPFragment : BaseVMFragment() {

    private val mViewModel: UserViewModel by viewModel()

    private var adapter: OTPAdapter? = null
    override fun getViewModel(): BaseViewModel = mViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_sercurity
    companion object {
        private val TAG = OTPFragment::class.java.name
        fun newInstance(): OTPFragment {
            val fragment = OTPFragment()
            /*fragment.arguments = Bundle().apply {
                putParcelableArrayList(TAG, otps)
            }*/
            return fragment
        }
    }
    override fun initView() {

        adapter =
            OTPAdapter(object : OnItemClickListener<OTP> {
                override fun onClick(item: OTP, position: Int) {
                    Log.d("", "")
                }

            })

        rcyOtp.adapter = adapter

        swr_pager.setOnRefreshListener {
            initData()
        }

    }

    override fun initData() {
        mViewModel.getListVerify(Settings.Account.id,Settings.Account.token)
            .observe(viewLifecycleOwner, Observer {
                if(it.datas.size == 0) tv_status.visible()
                else tv_status.gone()
                swr_pager.isRefreshing = false
                adapter?.setListItems(it.datas)
            })
    }

    override fun dismissLoading() {
        swr_pager.isRefreshing = false
    }

}