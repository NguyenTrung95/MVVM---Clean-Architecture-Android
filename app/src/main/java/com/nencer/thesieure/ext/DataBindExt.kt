package com.nencer.thesieure.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nencer.thesieure.AppContext
import com.nencer.thesieure.R

@BindingAdapter("url")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(AppContext).load(url)
        .placeholder(R.mipmap.ic_launcher)
        .into(imageView)
}