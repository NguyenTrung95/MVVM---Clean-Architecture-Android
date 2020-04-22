package com.nencer.thesieure.service.info.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString

class CountryResponse(): Parcelable{
    val datas: ArrayList<Country> = arrayListOf()

    constructor(parcel: Parcel) : this() {
    }

    constructor(jsonArr: JsonArray): this(){
        jsonArr.map{ jsonElement -> jsonElement.asJsonObject }.map{
            datas.add(Country(it))
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryResponse> {
        override fun createFromParcel(parcel: Parcel): CountryResponse {
            return CountryResponse(parcel)
        }

        override fun newArray(size: Int): Array<CountryResponse?> {
            return arrayOfNulls(size)
        }
    }
}
class Country() :  Parcelable {
    var id: Int? = 0
    var name: String? = ""
    var dial_code: Int? = 0
    var code: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        dial_code = parcel.readValue(Int::class.java.classLoader) as? Int
        code = parcel.readString()
    }


    constructor(jsonObject: JsonObject) : this() {
        id = jsonObject.optInt("id")
        name = jsonObject.optString("name")
        dial_code = jsonObject.optInt("dial_code")
        code = jsonObject.optString("code")

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(dial_code)
        parcel.writeString(code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }


}