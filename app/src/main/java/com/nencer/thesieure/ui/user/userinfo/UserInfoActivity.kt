package com.nencer.thesieure.ui.user.userinfo

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseVMActivity
import com.nencer.thesieure.base.dialog.*
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.helper.ToastUtil
import com.nencer.thesieure.helper.ToastUtil.context
import com.nencer.thesieure.service.info.InfoViewModel
import com.nencer.thesieure.ui.user.SignInActivity
import com.nencer.thesieure.service.user.model.response.UserInfo
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.content_navitation.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoActivity : BaseVMActivity() {
    private val userViewModel: UserViewModel by viewModel()
    private val infoViewModel: InfoViewModel by viewModel()
    override fun getViewModel(): BaseViewModel = userViewModel
    private val loadingDialog by lazy {
        LoadingIndicator(this)
    }
    private val adapter: UserInfoAdapter by lazy { UserInfoAdapter() }
    private val rows: MutableList<Pair<String, String>> = mutableListOf()
    private val fields: MutableList<Pair<String, String>> = mutableListOf()

    private var userInfo :UserInfo?=null
    private val country: ArrayList<String> = arrayListOf()
    companion object {
        private var TAG = UserInfoActivity::class.java.name
        fun start(context: Context, userInfo: UserInfo? = null) {
            val starter = Intent(context, UserInfoActivity::class.java)
            starter.putExtra(TAG, userInfo)

            context.startActivity(starter)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_user_info


    override fun initView() {
        userInfo = intent.getParcelableExtra<UserInfo>(TAG)
        tv_title.text = "Thông tin tài khoản"
        tv_title.setTextColor(resources.getColor(R.color.black_1))

        getCountry()

        userInfo?.let {
            rows.add(Pair("Tên đăng nhập:", userInfo?.username ?: ""))
            rows.add(Pair("Họ và tên:", userInfo?.name ?: ""))
            rows.add(Pair("Số điện thoại:", userInfo?.phone ?: ""))
            rows.add(Pair("Email:", userInfo?.email ?: ""))
            rows.add(
                Pair(
                    "Xác thực số điện thoại:",
                    if (userInfo?.verify_phone == 1) "Đã xác thực" else "Chưa xác thực"
                )
            )
            rows.add(
                Pair(
                    "Xác thực email:",
                    if (userInfo?.verify_email == 1) "Đã xác thực" else "Chưa xác thực"
                )
            )
            rows.add(
                Pair(
                    "Xác thực trên giấy tờ:",
                    if (userInfo?.verify_document == 1) "Đã xác thực" else "Chưa xác thực"
                )
            )
            rows.add(Pair("Ngày sinh:", userInfo?.birthday.toString()))

            rows.add(Pair("Ngày đăng kí:", userInfo?.created_at.toString()))
            userInfo?.wallet?.let { wallet ->
                rows.add(
                    Pair(
                        "Ví điện tử:",
                        "${wallet.number} (${wallet.balance_decode?.toBigDecimal()?.let { it1 ->
                            balance(it1)
                        }} ${wallet.currency_code})"
                    )
                )
            }

            rows.add(Pair("Giới tính:", if (userInfo?.gender == "male") "Nam" else "Nữ"))
            rows.add(Pair("Thành phố:", userInfo?.country_code ?: "VN"))


            adapter.setListItems(rows)


        }

        getFieldCanUpdate()

        rcyUserInfo.adapter = adapter
        rcyUserInfo.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        btn_edit_info.setOnClickListener {
            InfoEditerDialog.Builder(supportFragmentManager)
                .setTitle("SỬA THÔNG TIN TÀI KHOẢN")
                .setButtonName("Cập nhật thông tin")
                .setValue(userInfo)
                .setCountry(country)
                .setOnDataTextChangeListener { gioi_tinh, ngay_sinh, dat_nuoc,mc_2,email,view ->
                    val data : MutableList<Pair<String, String>> = mutableListOf()
                    var isExistMks = false

                    fields.forEach {
                        when (it.first.trim()){
                            "Giới tính".trim() -> {
                                data.add(Pair(it.second,if (gioi_tinh.trim()=="Nam") "male" else "female"))
                                //view.spn_gioi_tinh.isEnabled = false
                            }
                            "Ngày sinh".trim() ->{
                                data.add(Pair(it.second,ngay_sinh))
                                //view.edt_ngay_sinh.isEnabled = false
                            }
                            "Quốc gia".trim() -> {
                                data.add(Pair(it.second,dat_nuoc))
                                //view.edt_quoc_gia.isEnabled = false
                            }
                            "Mật khẩu cấp 2".trim() -> {
                                //view.edt_mat_khau_c2.isEnabled = false
                                data.add(Pair(it.second,mc_2))
                                isExistMks = true
                            }
                            "Email".trim() -> {
                                data.add(Pair(it.second,email))
                                //view.edt_email.isEnabled = false

                            }
                        }
                    }

                    updateInfo(data)

                }.setOnDismissListener {
                    reloadUserInfo()
                }.show()
        }


        btn_change_password.setOnClickListener {
            PasswordEditerDialog.Builder(supportFragmentManager)
                .setTitle("ĐỔI MẬT KHẨU")
                .setButtonName("Xác nhận")
                .setOnDataTextChangeListener { oldPass, newPass, rePass ->
                    ToastUtil.show("$oldPass $newPass $rePass")
                    oldPass.isEmpty().yes {
                        notifyError("Không được để trống họ tên")
                    }.otherwise {
                        newPass.isEmpty().yes {
                            notifyError("Không được để trống tên tài khoản")
                        }.otherwise {
                            rePass.isEmpty().yes {
                                notifyError("Bạn cần nhập lại mật khẩu mới")
                            }.otherwise {
                                if (newPass == rePass) {
                                    changePassword(oldPass, newPass)
                                } else notifyError("Mật khẩu không khớp. Vui lòng nhập lại.")
                            }
                        }
                    }
                }
                .show()
        }


        btn_back_button.setOnClickListener { onBackPressed() }

    }

    private fun reloadUserInfo(){
        userViewModel.getUserInfo(Settings.Account.id, Settings.Account.token ?: "")
            .observe(this, Observer {
                rows.clear()
                userInfo = UserInfo(it)

                rows.add(Pair("Tên đăng nhập:", userInfo?.username ?: ""))
                rows.add(Pair("Họ và tên:", userInfo?.name ?: ""))
                rows.add(Pair("Số điện thoại:", userInfo?.phone ?: ""))
                rows.add(Pair("Email:", userInfo?.email ?: ""))
                rows.add(
                    Pair(
                        "Xác thực số điện thoại:",
                        if (userInfo?.verify_phone == 1) "Đã xác thực" else "Chưa xác thực"
                    )
                )
                rows.add(
                    Pair(
                        "Xác thực email:",
                        if (userInfo?.verify_email == 1) "Đã xác thực" else "Chưa xác thực"
                    )
                )
                rows.add(
                    Pair(
                        "Xác thực trên giấy tờ:",
                        if (userInfo?.verify_document == 1) "Đã xác thực" else "Chưa xác thực"
                    )
                )
                rows.add(Pair("Ngày sinh:", userInfo?.birthday.toString()))

                rows.add(Pair("Ngày đăng kí:", userInfo?.created_at.toString()))
                userInfo?.wallet?.let { wallet ->
                    rows.add(
                        Pair(
                            "Ví điện tử:",
                            "${wallet.number} (${wallet.balance_decode?.toBigDecimal()?.let { it1 ->
                                balance(it1)
                            }} ${wallet.currency_code})"
                        )
                    )
                }

                rows.add(Pair("Giới tính:", if (userInfo?.gender == "male") "Nam" else "Nữ"))
                rows.add(Pair("Thành phố:", userInfo?.country_code ?: "VN"))


                adapter.setListItems(rows)


            })
    }

    private fun getFieldCanUpdate() {
        userViewModel.getFieldUpdateActive().observe(this, Observer {
            val gender = it.optJsonObject("gender")
            val birthday = it.optJsonObject("birthday")
            val country_code = it.optJsonObject("country_code")
            val mkc2 = it.optJsonObject("mkc2")
            val email = it.optJsonObject("email")

            gender?.let { field ->
                fields.add(Pair(field.optString("title"), field.optString("key")))
            }

            birthday?.let { field ->
                fields.add(Pair(field.optString("title"), field.optString("key")))
            }

            country_code?.let { field ->
                fields.add(Pair(field.optString("title"), field.optString("key")))
            }

            mkc2?.let { field ->
                fields.add(Pair(field.optString("title"), field.optString("key")))
            }

            email?.let { field ->
                fields.add(Pair(field.optString("title"), field.optString("key")))
            }
        })
    }

    private fun updateInfo(value: MutableList<Pair<String, String>>) {
        Settings.Payment.isPayment = true
        userViewModel.update(
            value
        ).observe(this, Observer {
            Settings.Payment.isPayment = false
            if (it.optInt("error_code") == 0) {
                val msg = it.optString("message")
                notifySuccess2(msg)
            }else notifyError("Đã có lỗi xảy ra!!")


        })
    }

    private fun getCountry(){
        infoViewModel.getCountry().observe(this, Observer {
            it.datas.map { it.name }.forEach {
                country.add(it?:"Không có dữ liệu")
            }

        })
    }

    private fun changePassword(oldPass: String = "", newPass: String = "") {
        userViewModel.changePassword(
            Settings.Account.id, Settings.Account.token,
            oldPass, newPass
        ).observe(this, Observer {
            if (it.optInt("error_code") == 0 && it.optString("message") == "success") {
                notifySuccess("Thay đổi mật khẩu thành công")
            } else notifyError("Đã có lỗi xảy ra")
        })
    }

    private fun notifyError(msg: String) {
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle(msg)
            .setType(AlertType.ERROR)
            .show()
    }

    private fun notifySuccess(msg: String) {
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle(msg)
            .setType(AlertType.SUCCESS)
            .setOnDismissListener {
                SignInActivity.start(this)
            }
            .show()
    }

    private fun notifySuccess2(msg: String) {
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle(msg)
            .setType(AlertType.SUCCESS)
            .show()
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun dismissLoading() {
        loadingDialog.dismiss()
    }


}