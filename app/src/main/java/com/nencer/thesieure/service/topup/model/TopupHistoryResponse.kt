package com.nencer.thesieure.service.topup.model

import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optJsonArray
import com.nencer.thesieure.ext.optString

class TopupHistoryResponse() {
    var items: MutableList<TopupHistoryInfo> = mutableListOf()
    var page:Int = 1
    constructor(jsonObject: JsonObject):this(){
        page = jsonObject.optInt("current_page")
        val results :MutableList<TopupHistoryInfo> = mutableListOf()
        jsonObject.optJsonArray("data")?.map{ jsonElement -> jsonElement.asJsonObject }?.map{
            results.add(TopupHistoryInfo(it))
        }
        items.addAll(results)
    }

}

class  TopupHistoryInfo() {
    var id: Int?=0
    var order_code: String?=""
    var user_info: String?=""
    var telco: String?=""
    var phone_number: String?=""
    var declared_value: Int?=0
    var completed_value: Int?=0
    var discount: String?=""
    var amount: Int?=0
    var currency_code: String?=""
    var status: String?=""
    var client_note: String?=""
    var payment: String?=""
    var created_at: String?=""


    constructor(jsonObject: JsonObject): this(){
        id = jsonObject.optInt("id")
        order_code = jsonObject.optString("order_code")
        user_info = jsonObject.optString("user_info")
        telco = jsonObject.optString("telco")
        phone_number = jsonObject.optString("phone_number")
        declared_value = jsonObject.optInt("declared_value")
        completed_value = jsonObject.optInt("completed_value")
        discount = jsonObject.optString("discount")
        amount = jsonObject.optInt("amount")
        currency_code = jsonObject.optString("currency_code")
        status = jsonObject.optString("status")
        client_note = jsonObject.optString("client_note")
        payment = jsonObject.optString("payment")
        created_at = jsonObject.optString("created_at")

    }

}