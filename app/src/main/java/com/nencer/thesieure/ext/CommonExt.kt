package com.nencer.thesieure.ext

import com.nencer.thesieure.config.Settings

fun isLogin(): Boolean = !Settings.Account.token.isNullOrBlank()
fun isPayment(): Boolean = Settings.Payment.isPayment

