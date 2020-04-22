package com.nencer.thesieure.service.payment.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optBigDecimal
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString
import java.math.BigDecimal

class CardList(){
    val datas: MutableList<Card> = mutableListOf()
    constructor(jsonArr: JsonArray): this(){
        jsonArr.map{ jsonElement -> jsonElement.asJsonObject }.map{
            datas.add(Card(it))
        }
    }
}
class Card() :  Parcelable{
    @Transient
    var isOrder = false
    @Transient
    var count = 0
    var id : Int?= 0
    var service_code: String?=""
    var name: String?=""
    var value:BigDecimal?= BigDecimal.ZERO
    var sku: String?= ""
    var sort_order : Int?=0
    var price: BigDecimal?= BigDecimal.ZERO
    var discount: BigDecimal?= BigDecimal.ZERO

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        service_code = parcel.readString()
        name = parcel.readString()
        sku = parcel.readString()
        sort_order = parcel.readValue(Int::class.java.classLoader) as? Int
    }


    constructor(jsonObject: JsonObject) : this() {
            id = jsonObject.optInt("id")
            service_code = jsonObject.optString("service_code")
            name = jsonObject.optString("name")
            value = jsonObject.optBigDecimal("value")
            sku = jsonObject.optString("sku")
            sort_order = jsonObject.optInt("sort_order")
            price = jsonObject.optBigDecimal("price")
            discount = jsonObject.optBigDecimal("discount")

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(service_code)
        parcel.writeString(name)
        parcel.writeString(sku)
        parcel.writeValue(sort_order)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Card> {
        override fun createFromParcel(parcel: Parcel): Card {
            return Card(parcel)
        }

        override fun newArray(size: Int): Array<Card?> {
            return arrayOfNulls(size)
        }
    }


}