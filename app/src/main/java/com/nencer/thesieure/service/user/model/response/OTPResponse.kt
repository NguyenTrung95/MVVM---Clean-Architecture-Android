package com.nencer.thesieure.service.user.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString

class OTPs(): Parcelable{
    val datas: ArrayList<OTP> = arrayListOf()

    constructor(parcel: Parcel) : this() {
    }

    constructor(jsonArr: JsonArray): this(){
        jsonArr.map{ jsonElement -> jsonElement.asJsonObject }.map{
            datas.add(OTP(it))
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OTPs> {
        override fun createFromParcel(parcel: Parcel): OTPs {
            return OTPs(parcel)
        }

        override fun newArray(size: Int): Array<OTPs?> {
            return arrayOfNulls(size)
        }
    }
}
class OTP() :  Parcelable {
    var id: Int? = 0
    var text: String? = ""
    var secret: String? = ""
    var type: String? = ""
    var created_at: String? = ""
    var expired_at: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        text = parcel.readString()
        secret = parcel.readString()
        type = parcel.readString()
        created_at = parcel.readString()
        expired_at = parcel.readString()
    }


    constructor(jsonObject: JsonObject) : this() {
        id = jsonObject.optInt("id")
        text = jsonObject.optString("text")
        secret = jsonObject.optString("secret")
        type = jsonObject.optString("type")
        created_at = jsonObject.optString("created_at")
        expired_at = jsonObject.optString("expired_at")


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(text)
        parcel.writeString(secret)
        parcel.writeString(type)
        parcel.writeString(created_at)
        parcel.writeString(expired_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OTP> {
        override fun createFromParcel(parcel: Parcel): OTP {
            return OTP(parcel)
        }

        override fun newArray(size: Int): Array<OTP?> {
            return arrayOfNulls(size)
        }
    }

}