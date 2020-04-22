package com.nencer.thesieure.service.wallet.response

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString

class PaygateList(){
    val datas: MutableList<Paygate> = mutableListOf()
    constructor(jsonArr: JsonArray): this(){
        jsonArr.map{ jsonElement -> jsonElement.asJsonObject }.map{
            datas.add(Paygate(it))
        }
    }
}
class Paygate(){
    var id : Int?= 0
    var code: String?=""
    var name: String?=""
    var paygate :String?=""
    constructor(jsonObject: JsonObject) : this() {
        id = jsonObject.optInt("id")
        code = jsonObject.optString("code")
        name = jsonObject.optString("name")
        paygate = jsonObject.optString("paygate")
    }

    override fun toString(): String {
        return name.toString()
    }
}