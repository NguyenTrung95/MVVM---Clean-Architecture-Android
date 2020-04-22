/*
 * *
 *  * Created by DinhDV on 8/10/18 9:14 AM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 8/10/18 9:14 AM
 *
 */

package com.nencer.thesieure.helper

import android.annotation.SuppressLint
import android.content.res.Resources


object DeviceUtil {

    @SuppressLint("HardwareIds")
    fun deviceID(): String {
        return "603cbc8b5aad18a1"
    }

    fun screenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun screenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun dpsToPixel(dps: Int): Int {
        return (dps * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }


}