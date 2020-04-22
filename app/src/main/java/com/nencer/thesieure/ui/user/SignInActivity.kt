package com.nencer.thesieure.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseDataBindVMActivity
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.databinding.ActivitySignInBinding
import com.nencer.thesieure.base.dialog.*
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.helper.ToastUtil
import com.nencer.thesieure.service.info.InfoViewModel
import com.nencer.thesieure.ui.main.MainActivity
import com.nencer.thesieure.service.user.model.UserLoginModel
import com.nencer.thesieure.service.user.model.response.LoginResponse
import com.nencer.thesieure.service.user.model.response.VerifyResponse
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseDataBindVMActivity<ActivitySignInBinding>() {

    private val mUserViewModel: UserViewModel by viewModel()
    private val mInfoViewModel: InfoViewModel by viewModel()

    private val loadingDialog by lazy {
        LoadingIndicator(this)
    }

    private val mUserLoginModel: UserLoginModel by lazy { UserLoginModel() }

    companion object {
        fun start(context: Context) {
            val start = Intent(context, SignInActivity::class.java)
            start.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(start)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_sign_in

    override fun getViewModel(): BaseViewModel = mUserViewModel

    override fun initView() {
        mUserLoginModel.username.set(Settings.Account.userName)
        mInfoViewModel.getDefault().observe(this, Observer {appInfo->
            appInfo.settings?.let {settings->
                settings.backendlogo?.let {
                    imv_logo.setImageUrl("${appInfo.url}${it}")
                    Settings.AppInfo.url = appInfo?.url?:""

                }

                settings.logo?.let {
                    Settings.AppInfo.appLogo =it
                }

            }

        })
        activity_sign_in_login_button.setOnClickListener {
            login()
        }

        activity_sign_in_let_go_text_view.setOnClickListener {
            activity_sign_in_password_edit_text.text.clear()
            SignUpActivity.start(this)
        }

        activity_sign_in_find_password_text_view.setOnClickListener {
            activity_sign_in_password_edit_text.text.clear()
            ForgotPasswordActivity.start(this)
        }

        mUserViewModel.errorMsg.observe(this, Observer {
            loadingDialog.dismiss()
            notifyError(it)
        })
        

    }

    override fun initData() {
        mDataBind.viewModel = mUserLoginModel
    }

    private fun login() {
        val username = mUserLoginModel.username.get().toString().trim()
        val password = mUserLoginModel.password.get().toString().trim()
        username.isEmpty().yes {
            notifyError("Không được để trống tên đăng nhập")
        }.otherwise {
            password.isEmpty().yes {
                notifyError("Không được để trống mật khẩu")
            }.otherwise {
                mUserViewModel.login(username, password).observe(this, Observer { rs ->
                    when (rs) {
                        is LoginResponse -> {
                            Settings.Account.userName = username
                            Settings.Account.password = password
                            Settings.Account.id = rs.id.toString()
                            Settings.Account.token = rs.api_token ?: ""

                            MainActivity.start(this)

                        }
                        is VerifyResponse -> {
                            VerifyDialog(this, onClickListener = { otp ->
                                mUserViewModel.verify(
                                    rs.phone ?: "",
                                    rs.token ?: "",
                                    otp,
                                    rs.session_id ?: ""
                                ).observe(this, Observer {
                                    val status = it.optString("status")

                                    if (status == "success") {
                                        val info = it.optJsonObject("info")
                                        val id = info?.optInt("id").toString()
                                        val token = info?.optString("api_token")
                                        Settings.Account.userName = username
                                        Settings.Account.password = password
                                        Settings.Account.id = id
                                        Settings.Account.token = token?:""
                                        MainActivity.start(this)

                                    } else {
                                        it.optString("message").isNotEmpty().yes {
                                            ToastUtil.show(it.optString("message"))
                                        }.otherwise {
                                            ToastUtil.show("Đã có lỗi gì đó xảy ra")
                                        }
                                    }
                                })
                            }).show()
                        }

                        else -> notifyError(" Không thể đăng nhập !")
                    }

                })

            }
        }

    }


    private fun notifyError(msg: String) {
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle(msg)
            .setType(AlertType.ERROR)
            .show()
    }

    private fun notifyNotify(msg: String) {
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle(msg)
            .setType(AlertType.WARNING)
            .show()
    }

    override fun handleError() {
        super.handleError()
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun dismissLoading() {
        loadingDialog.dismiss()
        activity_sign_in_password_edit_text.text.clear()

    }

}