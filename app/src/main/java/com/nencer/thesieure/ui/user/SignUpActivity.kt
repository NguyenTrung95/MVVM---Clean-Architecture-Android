package com.nencer.thesieure.ui.user

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseDataBindVMActivity
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.base.dialog.AlertType
import com.nencer.thesieure.base.dialog.CommonAlertDialog
import com.nencer.thesieure.base.dialog.LoadingIndicator
import com.nencer.thesieure.databinding.ActivitySignUpBinding
import com.nencer.thesieure.ext.otherwise
import com.nencer.thesieure.ext.yes
import com.nencer.thesieure.service.user.model.UserRegisterModel
import com.nencer.thesieure.service.user.model.response.RegisterResponse
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.content_navitation.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : BaseDataBindVMActivity<ActivitySignUpBinding>() {

    private val mViewModel: UserViewModel by viewModel()
    private val mUserRegisterModel: UserRegisterModel by lazy { UserRegisterModel() }
    private val loadingDialog by lazy {
        LoadingIndicator(this)
    }
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_sign_up

    override fun getViewModel(): BaseViewModel = mViewModel

    override fun initView() {
        tv_title.text = "Đăng ký"
        activity_register_button.setOnClickListener {
            register()
        }

        btn_back_button.setOnClickListener { onBackPressed() }

    }

    override fun initData() {
        mDataBind.viewModel = mUserRegisterModel
    }

    private fun register() {
        val name = mUserRegisterModel.name.get().toString().trim()
        val username = mUserRegisterModel.username.get().toString().trim()
        val phoneOrEmail = mUserRegisterModel.phone_email.get().toString().trim()
        val password = mUserRegisterModel.password.get().toString().trim()

        username.isEmpty().yes {
            notifyError("Không được để trống tên đăng nhập")
        }.otherwise {
            name.isEmpty().yes {
                notifyError("Không được để trống họ tên")
            }.otherwise {
                phoneOrEmail.isEmpty().yes {
                    notifyError("Không được để trống email/phone")
                }.otherwise {
                    password.isEmpty().yes {
                        notifyError("Không được để trống mật khẩu")
                    }.otherwise {
                        mViewModel.register(name,username,phoneOrEmail,password).observe(this,
                            Observer {
                                when(it){
                                    is RegisterResponse -> registerSuccess("Đăng kí thành công!!")
                                    is String ->  notifyError(it)
                                    else -> notifyError("Đã có lỗi xảy ra!!")
                                }

                            })
                    }
                }
            }
        }
    }

    private fun notifyError(msg: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setType(AlertType.ERROR)
            .show()
    }

    private fun registerSuccess(msg: String){
        CommonAlertDialog.Builder(supportFragmentManager)
            .setTitle( msg)
            .setType(AlertType.SUCCESS)
            .setOnDismissListener { 
                finish()
            }
            .show()
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun dismissLoading() {
        loadingDialog.dismiss()
    }


}