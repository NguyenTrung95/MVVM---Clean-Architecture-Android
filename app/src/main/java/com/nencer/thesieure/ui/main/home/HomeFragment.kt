package com.nencer.thesieure.ui.main.home

import android.os.Bundle
import androidx.lifecycle.Observer
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.nencer.thesieure.R
import com.nencer.thesieure.base.adapter.OnItemClickListener
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.base.fragment.BaseVMFragment
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.service.info.InfoViewModel
import com.nencer.thesieure.ui.main.home.adapter.MenuAdapter
import com.nencer.thesieure.ui.main.home.model.SlidersResponse
import com.nencer.thesieure.ui.user.userinfo.UserInfoActivity
import com.nencer.thesieure.service.user.model.response.UserInfo
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import com.nencer.thesieure.ui.payment.BuyCardActivity
import com.nencer.thesieure.ui.ExchangeActivity
import com.nencer.thesieure.ui.topup.TopupActivity
import com.nencer.thesieure.ui.wallet.DepositActivity
import com.nencer.thesieure.ui.wallet.TranferActivity
import com.nencer.thesieure.ui.wallet.WithDrawActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.txtBalance
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseVMFragment() {

    private val mInfoViewModel: InfoViewModel by viewModel()
    private val mUserViewModel : UserViewModel by viewModel()
    private lateinit var adapter: MenuAdapter

    private var userInfo: UserInfo?=null
    companion object {
        private val TAG = HomeFragment::class.java.name
        fun newInstance(wallet: UserInfo?=null): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(TAG, wallet)
            }
            return fragment
        }
    }
    override fun getViewModel(): BaseViewModel = mInfoViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun onResume() {
        super.onResume()
        getDataUser()
    }

    private fun getDataUser(){



        mUserViewModel.getUserInfo(Settings.Account.id, Settings.Account.token ?: "")
            .observe(this, Observer {
                userInfo = UserInfo(it)
                txtBalance.text = "${userInfo?.wallet?.balance_decode?.let { it1 -> balance(it1.toBigDecimal()) }}  ${userInfo?.wallet?.currency_code}"
                Settings.Account.currencyCode = userInfo?.wallet?.currency_code?:""

            })
    }

    override fun initView() {
        wwinfo.setBackgroundColor(resources.getColor(R.color.white_1))
        initAction()

        getDataUser()

        adapter = MenuAdapter(object :
            OnItemClickListener<Pair<Int, String>> {
            override fun onClick(item: Pair<Int, String>, position: Int) {
                when (item.first) {
                    R.drawable.muathe -> BuyCardActivity.start(
                        this@HomeFragment.context!!
                    )
                    R.drawable.taythe -> ExchangeActivity.start(
                        context!!
                    )
                    else -> TopupActivity.start(this@HomeFragment.context!!)
                }
            }

        })

        rcyMenu.adapter = adapter

        val listItems : MutableList<Pair<Int,String>> = mutableListOf()
        listItems.add(Pair(R.drawable.muathe,"Mua thẻ"))
        listItems.add(Pair(R.drawable.taythe,"Đổi thẻ"))
        listItems.add(Pair(R.drawable.naptien,"Nạp Topup"))

        adapter.setListItems(listItems)


    }
    private fun initAction(){

        action_account.setOnClickListener {
            UserInfoActivity.start(context!!,userInfo)
        }

        btnTranfer.setOnClickListener {
            TranferActivity.start(context!!)
        }

        image_slider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // You can listen here.
            }
        })

        btnDeposit.setOnClickListener {
            DepositActivity.start(this!!.context!!)
        }
        btn_withdraw.setOnClickListener{
            WithDrawActivity.start(this!!.context!!)
        }

    }



    override fun initData() {

        mInfoViewModel.getDefault().observe(this, Observer {
            wwinfo.loadDataWithBaseURL("", it.settings?.globalpopup_mes, "text/html", "UTF-8", "")
        })

        mInfoViewModel.getSliders().observe(this, Observer {
            val slider = SlidersResponse(it)

            val imageList = ArrayList<SlideModel>()

            slider.items.forEach {item->
                imageList.add(SlideModel("${Settings.AppInfo.url}${item.slider_image?:""}"))

            }
            image_slider.setImageList(imageList)

        })

    }


}