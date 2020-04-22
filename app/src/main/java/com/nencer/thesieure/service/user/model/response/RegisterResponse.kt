package com.nencer.thesieure.service.user.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery
import io.realm.annotations.PrimaryKey

open class RegisterResponse() : Parcelable,RealmObject() {
    @PrimaryKey
    var id: Int?= 0
    var username: String? = ""
    var name: String?=""
    var email: String?=""
    var phone: String?=""
    var group: String?=""
    var country_code: String?= ""
    var parent_id: Int?= 0
    var ip: String?= ""
    var ref: String?=""
    var status: Int?= 0
    var updated_at: String?=""
    var created_at: String?= ""

    constructor(json : JsonObject):this(){
        id = json.optInt("id")
        name = json.optString("name")
        username = json.optString("username")
        email = json.optString("email")
        phone = json.optString("phone")
        country_code = json.optString("country_code")
        parent_id = json.optInt("parent_id")
        group = json.optString("group")
        ip = json.optString("ip")
        ref = json.optString("ref")
        status = json.optInt("status")
        updated_at = json.optString("updated_at")
        created_at = json.optString("created_at")

    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        username = parcel.readString()
        name = parcel.readString()
        email = parcel.readString()
        phone = parcel.readString()
        group = parcel.readString()
        country_code = parcel.readString()
        parent_id = parcel.readValue(Int::class.java.classLoader) as? Int
        ip = parcel.readString()
        ref = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        updated_at = parcel.readString()
        created_at = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(group)
        parcel.writeString(country_code)
        parcel.writeValue(parent_id)
        parcel.writeString(ip)
        parcel.writeString(ref)
        parcel.writeValue(status)
        parcel.writeString(updated_at)
        parcel.writeString(created_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisterResponse> {
        override fun createFromParcel(parcel: Parcel): RegisterResponse {
            return RegisterResponse(parcel)
        }

        override fun newArray(size: Int): Array<RegisterResponse?> {
            return arrayOfNulls(size)
        }

        private val query: RealmQuery<RegisterResponse>
            get() = Realm.getDefaultInstance()!!.where(RegisterResponse::class.java)

        fun findById(id: Int): RegisterResponse? {
            return query.equalTo("id", id).findFirst()
        }
    }
}