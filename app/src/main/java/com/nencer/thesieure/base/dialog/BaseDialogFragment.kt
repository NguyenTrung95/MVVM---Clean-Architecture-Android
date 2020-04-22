package com.nencer.thesieure.base.dialog

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.nencer.thesieure.ext.dpToPx
import kotlin.math.min

/**
 * Created by nguyentrung on 2019-06-20
 */
abstract class BaseDialogFragment : DialogFragment() {

    protected abstract fun getLayoutResId(): Int
    protected open val sizePercent = 0.85F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onResume() {
        val window = dialog?.window ?: return super.onResume()

        val size = Point()
        val display = window.windowManager.defaultDisplay
        display.getSize(size)

        val width = min((size.x * sizePercent).toInt(), dpToPx(400))

        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)

        super.onResume()
    }

    override fun onDestroyView() {
        if (dialog != null && retainInstance) {
            dialog?.setDismissMessage(null)
        }
        super.onDestroyView()
    }
}