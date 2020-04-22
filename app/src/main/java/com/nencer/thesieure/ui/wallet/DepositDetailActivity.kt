package com.nencer.thesieure.ui.wallet

import android.content.Context
import android.content.Intent
import android.view.View
import com.nencer.thesieure.R
import com.nencer.thesieure.base.activity.BaseActivity
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.ext.*
import com.nencer.thesieure.helper.ClipboardUtil
import com.nencer.thesieure.helper.ToastUtil
import kotlinx.android.synthetic.main.activity_deposit_detaik.*
import kotlinx.android.synthetic.main.activity_withdraw.btn_back_button


class DepositDetailActivity : BaseActivity() {


    override fun getLayoutId(): Int = R.layout.activity_deposit_detaik

    companion object {
        fun start(
            context: Context,
            don_hang: String,
            vi_nhan: String,
            so_tien: String,
            phi: String,
            hinh_thuc: String,
            ngay_tao: String,
            trang_thai: String,
            chu_tk: String,
            stk: String,
            so_atm: String,
            chi_nhanh: String,
            noi_dung_chuyen: String,
            so_tien_chuyen: String,
            bank_name : String,
            isDeposit : Boolean
        ) {
            val start = Intent(context, DepositDetailActivity::class.java)
            start.putExtra("don_hang", don_hang)
            start.putExtra("vi_nhan", vi_nhan)
            start.putExtra("so_tien", so_tien)
            start.putExtra("phi", phi)
            start.putExtra("ngay_tao", ngay_tao)
            start.putExtra("hinh_thuc", hinh_thuc)
            start.putExtra("trang_thai", trang_thai)
            start.putExtra("chu_tk", chu_tk)
            start.putExtra("stk", stk)
            start.putExtra("so_atm", so_atm)
            start.putExtra("chi_nhanh", chi_nhanh)
            start.putExtra("noi_dung_chuyen", noi_dung_chuyen)
            start.putExtra("so_tien_chuyen", so_tien_chuyen)
            start.putExtra("bank_name", bank_name)
            start.putExtra("isDeposit", isDeposit)

            context.startActivity(start)
        }
    }


    override fun initView() {
        val don_hang = intent.getStringExtra("don_hang") ?: ""
        val vi_nhan = intent.getStringExtra("vi_nhan") ?: ""
        val so_tien = intent.getStringExtra("so_tien") ?: ""
        val phi = intent.getStringExtra("phi") ?: ""
        val ngay_tao = intent.getStringExtra("ngay_tao") ?: ""
        val hinh_thuc = intent.getStringExtra("hinh_thuc") ?: ""
        val trang_thai = intent.getStringExtra("trang_thai") ?: ""
        val chu_tk = intent.getStringExtra("chu_tk") ?: ""
        val stk = intent.getStringExtra("stk") ?: ""
        val so_atm = intent.getStringExtra("so_atm") ?: ""
        val chi_nhanh = intent.getStringExtra("chi_nhanh") ?: ""
        val noi_dung_chuyen = intent.getStringExtra("noi_dung_chuyen") ?: ""
        val so_tien_chuyen = intent.getStringExtra("so_tien_chuyen") ?: ""

        val bank_name = intent.getStringExtra("bank_name") ?: ""
        val isDeposit = intent.getBooleanExtra("isDeposit", false)

        /*if (isDeposit) {
            lnBank.visibility = View.GONE
            lnctk.visibility = View.VISIBLE
        } else {
            lnBank.visibility = View.VISIBLE
            lnctk.visibility = View.GONE

        }*/
        tv_bank.text = bank_name
        tv_don_hang_value.text = don_hang
        tv_vi_nhan.text = vi_nhan
        tv_so_tien.text = balance(so_tien.toInt()) + " " + Settings.Account.currencyCode
        tv_phi.text = balance(phi.toInt()) + " " + Settings.Account.currencyCode
        tv_ngay_tao.text = ngay_tao
        tv_hinh_thuc.text = hinh_thuc
        tv_chu_tk.text = chu_tk
        tv_stk.text = stk
        tv_so_atm.text = so_atm
        tv_chi_nhanh.text = chi_nhanh
        tv_nd_ck.text = noi_dung_chuyen
        tv_stc.text = balance(so_tien_chuyen.toInt()) + " " + Settings.Account.currencyCode

        when (trang_thai.trim()) {
            "none" -> tv_trang_thai.text = "Chờ  thanh toán"
            "completed " -> tv_trang_thai.text = "Đã hoàn thành"
            "canceled  " -> tv_trang_thai.text = "Hủy"



        }
        btncopystk.setOnClickListener{
            ClipboardUtil.copyToClipBoard(this, "Số tài khoản: ", tv_stk.text.toString())
            ToastUtil.show("Đã copy số tài khoản")
        }
        btncopycontent.setOnClickListener{
            ClipboardUtil.copyToClipBoard(this, "Nội dung: ", tv_nd_ck.text.toString())
            ToastUtil.show("Đã copy nội dung")
        }
        so_atm.isEmpty().yes {
            ll_atm.gone()
        }.otherwise { ll_atm.visible() }


        btn_back_button.setOnClickListener {
            onBackPressed()
        }


    }


}