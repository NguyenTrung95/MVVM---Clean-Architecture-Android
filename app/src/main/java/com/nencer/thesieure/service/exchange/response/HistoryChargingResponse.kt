package com.nencer.thesieure.service.exchange.response

import com.google.gson.annotations.SerializedName

data class HistoryChargingResponse(
    @SerializedName("current_page")
    var current_page: Int = 0,
    @SerializedName("data") var data: MutableList<HistoryChargingInfo> = mutableListOf()
)

data class HistoryChargingInfo(

    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("user")
    var user: Int? = 0,
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("serial")
    var serial: String? = "",
    @SerializedName("declared_value")
    var declared_value: Int = 0,
    @SerializedName("real_value")
    var real_value: Int = 0,
    @SerializedName("penalty")
    var penalty: Int = 0,
    @SerializedName("amount")
    var amount: Int = 0,
    @SerializedName("fees")
    var fees: Int = 0,
    @SerializedName("currency_code")
    var currency_code: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("error_message")
    var description: String = "",
    @SerializedName("created_at")
    var created_at: String = "",
    @SerializedName("telco")
    var telco: String = ""

)
