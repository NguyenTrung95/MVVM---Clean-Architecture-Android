package com.nencer.thesieure.service.user.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optJsonObject
import com.nencer.thesieure.ext.optString
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery
import io.realm.annotations.PrimaryKey

open class LoginResponse()  : Parcelable , RealmObject() {
    @PrimaryKey
    var id: Int? = 0
    var username: String?=""
    var name: String?=""
    var email: String?=""
    var phone: String?=""
    var country_code: String?= ""
    var gender: String?=""
    var group: Int?= 0
    var status: Int?= 0
    var verify_phone: Int?=0
    var verify_email: Int?=0
    var birthday: String?=""

    var api_token: String?= ""

    constructor(json :JsonObject):this(){
        val data = json.optJsonObject("info")
        id = data?.optInt("id")
        name = data?.optString("name")
        username = data?.optString("username")
        email = data?.optString("email")
        phone = data?.optString("phone")
        country_code = data?.optString("country_code")
        gender = data?.optString("gender")
        group = data?.optInt("group")
        api_token = data?.optString("api_token")
        status = data?.optInt("status")

    }


    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        username = parcel.readString()
        name = parcel.readString()
        email = parcel.readString()
        phone = parcel.readString()
        country_code = parcel.readString()
        gender = parcel.readString()
        group = parcel.readInt()
        status = parcel.readInt()
        verify_phone = parcel.readInt()
        verify_email = parcel.readInt()
        birthday = parcel.readString()
        api_token = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id?:0)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(country_code)
        parcel.writeString(gender)
        parcel.writeInt(group?:0)
        parcel.writeInt(status?:0)
        parcel.writeInt(verify_phone?:0)
        parcel.writeInt(verify_email?:0)
        parcel.writeString(birthday)
        parcel.writeString(api_token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResponse> {
        override fun createFromParcel(parcel: Parcel): LoginResponse {
            return LoginResponse(parcel)
        }

        override fun newArray(size: Int): Array<LoginResponse?> {
            return arrayOfNulls(size)
        }
    }

    private val query:RealmQuery<LoginResponse>
        get() = Realm.getDefaultInstance()!!.where(LoginResponse::class.java)

    fun findById(code: String): LoginResponse? {
        return query.equalTo("id", code).findFirst()
    }

}