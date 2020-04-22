package com.nencer.thesieure.ext

import android.content.Context
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey

object GlideApp {
    fun with(context: Context): RequestManager {
        return Glide.with(context).expire(86400)
    }
}

fun RequestManager.placeholder(@DrawableRes image:Int): RequestManager {
    return this.applyDefaultRequestOptions(RequestOptions()
            .placeholder(image)
            .error(image))
}

fun RequestManager.expire(seconds:Int): RequestManager {
    return this.applyDefaultRequestOptions(RequestOptions()
            .signature(ObjectKey(System.currentTimeMillis()/1000/seconds)))
}