package com.nencer.thesieure.ui.main.home.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optJsonArray
import com.nencer.thesieure.ext.optString
class OrderData(): Parcelable {
    val listCardInfo : MutableList<CardInfo>  = mutableListOf()
    var order_code : String?=""
    var status: String?=""
    var pay_amount: String?=""
    var currency_code: String?=""

    constructor(parcel: Parcel) : this() {
//        if (Build.VERSION.SDK_INT >= 29) parcel.readParcelableList(listCardInfo, CardInfo::class.java.classLoader)
//        else
        parcel.readList(listCardInfo as List<CardInfo>, CardInfo::class.java.classLoader)
        order_code = parcel.readString()
        status = parcel.readString()
        pay_amount = parcel.readString()
        currency_code = parcel.readString()
    }

    constructor(jsonObject: JsonObject) : this() {
        order_code = jsonObject.optString("order_code")
        jsonObject.optJsonArray("cardinfo")?.map {jsonElement -> jsonElement.asJsonObject }?.map {
            listCardInfo.add(CardInfo(it))
        }

        status = jsonObject.optString("status")
        pay_amount = jsonObject.optInt("pay_amount").toString()
        currency_code = jsonObject.optString("currency_code")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(listCardInfo as List<CardInfo>?)
        parcel.writeString(order_code)
        parcel.writeString(status)
        parcel.writeString(pay_amount)
        parcel.writeString(currency_code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderData> {
        override fun createFromParcel(parcel: Parcel): OrderData {
            return OrderData(parcel)
        }

        override fun newArray(size: Int): Array<OrderData?> {
            return arrayOfNulls(size)
        }
    }

}

class CardInfo(): Parcelable {
     var id: Int?=0
     var name: String?= ""
     var serial: String?=""
     var code: String?=""
     var expire: String?=""
     var user_id: String?=""
     var order_code: String?=""
     var softcard_order_id: String?=""
     var created_at: String?=""
     var updated_at: String?=""

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        serial = parcel.readString()
        code = parcel.readString()
        expire = parcel.readString()
        user_id = parcel.readString()
        order_code = parcel.readString()
        softcard_order_id = parcel.readString()
        created_at = parcel.readString()
        updated_at = parcel.readString()
    }

    constructor(jsonObject: JsonObject) : this() {
         id = jsonObject.optInt("id")
        name = jsonObject.optString("name")
         serial = jsonObject.optString("serial")
         code = jsonObject.optString("code")
        expire = jsonObject.optString("expire")
        user_id = jsonObject.optInt("user_id").toString()
        order_code = jsonObject.optString("order_code")
        softcard_order_id = jsonObject.optInt("softcard_order_id").toString()
        created_at = jsonObject.optString("created_at")
        updated_at = jsonObject.optString("updated_at")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(serial)
        parcel.writeString(code)
        parcel.writeString(expire)
        parcel.writeString(user_id)
        parcel.writeString(order_code)
        parcel.writeString(softcard_order_id)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardInfo> {
        override fun createFromParcel(parcel: Parcel): CardInfo {
            return CardInfo(parcel)
        }

        override fun newArray(size: Int): Array<CardInfo?> {
            return arrayOfNulls(size)
        }
    }

}