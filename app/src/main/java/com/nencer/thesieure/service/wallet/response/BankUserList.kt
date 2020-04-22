package com.nencer.thesieure.service.wallet.response

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString

class BankUserList(){
    val datas: MutableList<BankUser> = mutableListOf()
    constructor(jsonArr: JsonArray): this(){
        jsonArr.map{ jsonElement -> jsonElement.asJsonObject }.map{
            datas.add(BankUser(it))
        }
    }
}
class BankUser(){

//    "id": 46,
//    "user_id": 25,
//    "code": "VCB",
//    "paygate_code": "Localbank_VCB",
//    "acc_num": "0031001106311",
//    "acc_name": "Nguyen Van A",
//    "branch": "HN",
//    "card_num": null,
//    "approved": 1,
//    "created_at": "2020-01-01 00:15:45",
//    "updated_at": "2020-01-01 00:15:45",
//    "bankname": "Ngân hàng Vietcombank"


    var id : Int?= 0
    var user_id : Int?= 0
    var code: String?=""
    var name: String?=""
    var paygate_code :String?=""
    var acc_num :String?=""
    var acc_name :String?=""
    var branch :String?=""
    var approved :String?=""
    var created_at :String?=""
    var bankname :String?=""
    var card_num :String?=""
    constructor(jsonObject: JsonObject) : this() {
        id = jsonObject.optInt("id")
        code = jsonObject.optString("code")
        name = jsonObject.optString("name")
        paygate_code = jsonObject.optString("paygate_code")
        user_id = jsonObject.optInt("user_id")
        acc_num = jsonObject.optString("acc_num")
        acc_name = jsonObject.optString("acc_name")
        branch = jsonObject.optString("branch")
        approved = jsonObject.optString("approved")
        created_at = jsonObject.optString("created_at")
        bankname = jsonObject.optString("bankname")
        card_num = jsonObject.optString("card_num")
    }

    override fun toString(): String {
        if(bankname.isNullOrEmpty()){
            return name.toString()
        }else{
            return acc_name.toString().plus(" - "+bankname.toString())
        }

    }
}