package com.nencer.thesieure.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseVMActivity
import com.nencer.thesieure.base.adapter.ViewPagerAdapter
import com.nencer.thesieure.base.dialog.AlertType
import com.nencer.thesieure.base.dialog.CommonAlertDialog
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import com.nencer.thesieure.ui.main.bank.BankAllFragment
import com.nencer.thesieure.ui.main.history.HistoryFragment
import com.nencer.thesieure.ui.main.home.HomeFragment
import com.nencer.thesieure.ui.main.news.NewsFragment
import com.nencer.thesieure.ui.main.sercurity.OTPFragment
import com.nencer.thesieure.ui.user.SignInActivity
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class  MainActivity : BaseVMActivity() {
    private var mViewPagerAdapter: ViewPagerAdapter? = null

    private val userModel : UserViewModel by viewModel()



    companion object {
        var instance : MainActivity? = null
        private var TAG = MainActivity::class.java.name
        fun start(context : Context){
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }


    }
    fun logout(){
         val intent = Intent(this, SignInActivity::class.java)
        intent.putExtra("crash", true)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
            or Intent.FLAG_ACTIVITY_CLEAR_TASK
            or Intent.FLAG_ACTIVITY_NEW_TASK
        )
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val mgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent)
        System.exit(0)
    }

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun initView() {
        instance = this
        val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
        val version = pInfo.versionName
        userModel.updateVersion(version).observe(this, Observer {
            val error_code = it.optInt("error_code")
            val message = it.optString("message")

            if(error_code == 1) {
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setCancelable(false)
                    .defaultAction("Đồng ý") { dialog, index ->
                        try {
                            val uri =
                                Uri.parse("market://details?id=$packageName")
                            val goMarket = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(goMarket)
                        } catch (e: ActivityNotFoundException) {
                            val uri =
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            val goMarket = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(goMarket)
                        }
                    }.show()
            }
        })


        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        mViewPagerAdapter?.addFragment("Trang chủ",
            HomeFragment.newInstance())
        mViewPagerAdapter?.addFragment("Tin tức",
            NewsFragment()
        )
        mViewPagerAdapter?.addFragment("Lịch sử",
            HistoryFragment()
        )
        mViewPagerAdapter?.addFragment("Ngân hàng",
            BankAllFragment()
        )
        mViewPagerAdapter?.addFragment("OTP", OTPFragment.newInstance())
        viewpager_container.adapter = mViewPagerAdapter
        viewpager_container.offscreenPageLimit = 5
        activity_main_tab_layout.setupWithViewPager(viewpager_container)
        viewpager_container.currentItem = 0
        initTabBar()

        GlideApp.with(this).placeholder(R.mipmap.ic_launcher)
            .load("${Settings.AppInfo.url}${Settings.AppInfo.appLogo}")
            .into( imvLogo)

        imv_power.setOnClickListener {
            showLogoutDialog()

        }


    }

    override fun onBackPressed() {
        showLogoutDialog()
    }

    private fun showLogoutDialog() {
            AlertDialog.Builder(this)
                .setMessage("Đăng xuất tài khoản")
                .defaultAction("Đồng ý") { dialog, index ->
                    userModel.logout(Settings.Account.id,Settings.Account.token).observe(this, Observer {
                        if (it.optInt("error_code")== 0) finish()
                        else {
                            val msg = it.optString("message")
                            CommonAlertDialog.Builder(supportFragmentManager)
                                .setTitle(msg)
                                .setType(AlertType.ERROR)
                                .show()
                        }
                    })
                }
                .cancelAction("Không").show()


    }

    private fun initTabBar(){
        for (i in 0 until activity_main_tab_layout.tabCount) {
            val drawable = ContextCompat.getDrawable(this, tabIconList[i])
            drawable!!.clearColorFilter()
            activity_main_tab_layout.getTabAt(i)!!.setIcon(tabIconList[i])
        }
        activity_main_tab_layout.setTabTextColors(resources.getColor(R.color.color_tab_default),resources.getColor(R.color.blue_1))

        activity_main_tab_layout.apply {
            clearFindViewByIdCache()
            tabGravity = TabLayout.GRAVITY_FILL
            addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.icon!!.setTint(resources.getColor(R.color.color_tab_default))

                }
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.icon!!.setTint(resources.getColor(R.color.blue_1))


                    when(tab.position){
                        0-> {
                            tv_title?.gone()
                            imvLogo?.visible()
                            imv_power?.visible()
                            btn_add?.gone()

                        }
                        1 -> {
                            tv_title?.visible()
                            imvLogo?.gone()
                            imv_power?.gone()
                            btn_add?.gone()
                            tv_title?.text = "Tin tức"
                        }
                        2 -> {
                            tv_title?.visible()
                            imvLogo?.gone()
                            imv_power?.gone()
                            btn_add?.gone()
                            tv_title?.text = "Lịch sử"
                        }
                        3 -> {
                            tv_title?.visible()
                            imvLogo?.gone()
                            imv_power?.gone()
                            btn_add?.visible()
                            tv_title?.text = "Ngân hàng"
                        }
                        4 -> {
                            tv_title?.visible()
                            imvLogo?.gone()
                            imv_power?.gone()
                            btn_add?.gone()
                            tv_title?.text = "OTP"
                        }
                    }
                }
            })
        }
    }


    override fun getViewModel(): BaseViewModel = userModel


    private val tabIconList = listOf(
        R.drawable.ic_home,
        R.drawable.ic_news,
        R.drawable.ic_history,
        R.drawable.ic_bank,
        R.drawable.ic_sercurity
    )

}
