package com.nencer.thesieure.service.payment.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("current_page")
    var current_page: Int = 0,
    @SerializedName("data") var data: MutableList<HistoryOrder> = mutableListOf()
)

data class HistoryOrder(
    @SerializedName("current_page")
    var id: Int = 0,
    @SerializedName("order_code")
    var order_code: String? = "",
    @SerializedName("currency_code")
    var currency_code: String? = "",
    @SerializedName("pay_amount")
    var pay_amount: Int? = 0,
    @SerializedName("status")
    var status: String = "Thất bại",
    @SerializedName("payment")
    var payment: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("payer_id")
    var payer_id: Int = 0,
    @SerializedName("cardinfo")
    var cardinfo: List<Info> = listOf()
)

data class Info(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("serial")
    var serial: String? = "",
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("user_id")
    var user_id: Int? = 0,
    @SerializedName("order_code")
    var order_code: String? = "",
    @SerializedName("softcard_order_id")
    var softcard_order_id: Int? = 0,
    @SerializedName("created_at")
    var created_at: String? = "",
    @SerializedName("updated_at")
    var updated_at: String? = ""
)