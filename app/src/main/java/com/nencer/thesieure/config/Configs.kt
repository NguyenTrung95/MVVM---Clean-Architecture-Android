package com.nencer.thesieure.config

import android.os.Build
import java.util.*

/**
 * Oauth
 */
object Configs {
    val FINGERPRINT: String by lazy { Build.FINGERPRINT + UUID.randomUUID().toString() }

    const val MODE_KEY = "222222"

    enum class REMEMBER(val status: String) {
        YES("1"),
        NO("0")
    }

    const val PAGE_SIZE = 10
}