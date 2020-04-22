/*
 * Created by Nguyen Trung@mail: nguyennguyentrungtnt@gmail.com on 2019-09-12..
 */
package com.nencer.thesieure.base.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import com.nencer.thesieure.R
import kotlin.system.exitProcess

class ExceedRecommendWarningDialog(context: Context) : Dialog(context) {

    init {
        setCancelable(true)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window?.run {
            setBackgroundDrawable(ColorDrawable(0))
        } ?: exitProcess(0)

        setContentView(R.layout.dialog_exceed_recommend_warning)

        // Cài đặt ngẫu nhiên hình ảnh cảnh báo
        findViewById<ImageView>(R.id.dialog_exceed_recommend_warning_image_view).setImageResource(R.drawable.loading_20)
        findViewById<View>(R.id.dialog_exceed_recommend_warning_parent_view).setOnClickListener { cancel() }
    }

}