package com.nencer.thesieure.service.wallet.response

import com.google.gson.annotations.SerializedName

data class HistoryDepositResponse(
    @SerializedName("current_page")
    var current_page: Int = 0,
    @SerializedName("data") var data: MutableList<HistoryInfo> = mutableListOf()
)

data class HistoryInfo(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("order_code")
    var order_code: String? = "",
    @SerializedName("order_type")
    var order_type: String? = "",
    @SerializedName("module")
    var module: String? = "",
    @SerializedName("currency_code")
    var currency_code: String = "",
    @SerializedName("payer_wallet")
    var payer_wallet: String = "",
    @SerializedName("payee_wallet")
    var payee_wallet: String = "",
    @SerializedName("payer_id")
    var payer_id: Int = 0,
    @SerializedName("net_amount")
    var net_amount: Int = 0,
    @SerializedName("discount")
    var discount: String = "",
    @SerializedName("fees")
    var fees: Int = 0,
    @SerializedName("pay_amount")
    var pay_amount: Int = 0,
    @SerializedName("paygate_code")
    var paygate_code: String = "",
    @SerializedName("bank_code")
    var bank_code: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("creator")
    var creator: String = "",
    @SerializedName("payment_type")
    var payment_type: String = "",
    @SerializedName("created_at")
    var created_at: String = "",
    @SerializedName("bank_info")
    var bankinfo: BankInfo? = null,
    @SerializedName("payer_info")
    var payerInfo: PayerInfo? = null,
    @SerializedName("payee_info")
    var payeeInfo: PayeeInfo? = null,
    @SerializedName("clientname")
    var clientname: ClientName? = null,
    @SerializedName("payer_before_balance")
    var my_before_balance:Double = 0.0,
    @SerializedName("payer_after_balance")
    var my_after_balance:Double = 0.0,
    @SerializedName("my_pay_amount")
    var my_pay_amount: String = ""
    )

data class ClientName(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("email")
    var email: String? = ""

)

data class BankInfo(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("account_name")
    var account_name: String? = "",
    @SerializedName("account_number")
    var account_number: String? = "",
    @SerializedName("account_branch")
    var account_branch: String? = "",
    @SerializedName("account_card_number")
    var account_card_number: String? = ""

)

data class PayerInfo(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("email")
    var email: String? = ""
)

data class PayeeInfo(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("email")
    var email: String? = ""
)