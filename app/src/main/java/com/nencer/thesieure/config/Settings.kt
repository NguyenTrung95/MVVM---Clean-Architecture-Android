package com.nencer.thesieure.config

import com.nencer.thesieure.base.constant.Constant
import com.nencer.thesieure.data.storage.Preference

object Settings {

    object AppInfo {
        var url by Preference(Constant.URL, "")
        var appLogo by Preference(Constant.LOGO_URL, "")
    }

    object Account {
        var id by Preference(Constant.USER_ID, "")
        var token by Preference(Constant.USER_TOKEN_API, "")
        var userName by Preference(Constant.USER_NAME, "")
        var password by Preference(Constant.USER_PASSWORD, "")
        var currencyCode by Preference(Constant.CURRENCY_CODE, "")
    }

    object Payment{
        var isPayment by Preference(Constant.IS_PAYMENT,false)
    }
}