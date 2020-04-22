package com.nencer.thesieure.customview
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.nencer.thesieure.R
import kotlinx.android.synthetic.main.button_nencer.view.*

class NencerButton(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    init {
        inflate(context, R.layout.button_nencer, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.NencerButton)

        if (attributes.getString(R.styleable.NencerButton_title).isNullOrEmpty()) tv_title.visibility = View.GONE
        else  tv_title.text = attributes.getString(R.styleable.NencerButton_title)

        attributes.getResourceId(R.styleable.NencerButton_img,0)?.let { imv_logo.setImageResource(it) }
        attributes.recycle()

    }

}