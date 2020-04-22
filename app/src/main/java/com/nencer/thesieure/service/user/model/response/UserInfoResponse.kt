package com.nencer.thesieure.service.user.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optBigDecimal
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optJsonObject
import com.nencer.thesieure.ext.optString

open class UserInfo() : Parcelable {
    var id: Int? = 0
    var username: String? = ""
    var name: String? = ""
    var email: String? = ""
    var phone: String? = ""
    var gender: String? =""
    var group: Int? = 0
    var verify_phone: Int? = 0
    var verify_email: Int? = 0
    var verify_document: Int? = 0
    var created_at: String? = ""
     var updated_at: String? =""
    var wallet: Wallet? = null
    var birthday: String? = ""
    var country_code: String?="VN"
    var mkc2: String?=""
    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        username = parcel.readString()
        name = parcel.readString()
        email = parcel.readString()
        phone = parcel.readString()
        gender = parcel.readString()
        group = parcel.readValue(Int::class.java.classLoader) as? Int
        verify_phone = parcel.readValue(Int::class.java.classLoader) as? Int
        verify_email = parcel.readValue(Int::class.java.classLoader) as? Int
        verify_document = parcel.readValue(Int::class.java.classLoader) as? Int
        created_at = parcel.readString()
        updated_at = parcel.readString()
        wallet = parcel.readParcelable(Wallet::class.java.classLoader)
        birthday = parcel.readString()
        country_code = parcel.readString()
        mkc2 = parcel.readString()
    }


    constructor(jsonObject: JsonObject) : this() {
        id = jsonObject.optInt("id")
        username = jsonObject.optString("username")
        name = jsonObject.optString("name")
        email = jsonObject.optString("email")
        phone = jsonObject.optString("phone")
        gender = jsonObject.optString("gender")
        group = jsonObject.optInt("group")
        verify_phone = jsonObject.optInt("verify_phone")
        verify_email = jsonObject.optInt("verify_email")
        verify_document = jsonObject.optInt("verify_document")
        created_at = jsonObject.optString("created_at")
        updated_at = jsonObject.optString("updated_at")
        birthday = jsonObject.optString("birthday")
        country_code = jsonObject.optString("country_code")
        mkc2 = jsonObject.optString("mkc2")

        val jsonObjectWallet = jsonObject.optJsonObject("wallet")
        jsonObjectWallet?.let {
           wallet = Wallet(jsonObjectWallet)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(gender)
        parcel.writeValue(group)
        parcel.writeValue(verify_phone)
        parcel.writeValue(verify_email)
        parcel.writeValue(verify_document)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
        parcel.writeParcelable(wallet, flags)
        parcel.writeString(birthday)
        parcel.writeString(country_code)
        parcel.writeString(mkc2)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }

}

open class Wallet() :  Parcelable{
    var id : Int?= 0
    var number: String?=""
    var currency_code: String?=""
    var user:String?=""
    var balance: String?=""
    var balance_decode : String?= ""
    var pending_balance: String?= ""
    var checksum: String?=""
    var status: Int?= 0
    var locked: Int?= 0
    var created_at:String?=""
    var updated_at: String?=""

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        number = parcel.readString()
        currency_code = parcel.readString()
        user = parcel.readString()
        balance = parcel.readString()
        balance_decode = parcel.readString()
        pending_balance = parcel.readString()
        checksum = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        locked = parcel.readValue(Int::class.java.classLoader) as? Int
        created_at = parcel.readString()
        updated_at = parcel.readString()
    }

    constructor(jsonObject: JsonObject) : this() {
            id = jsonObject.optInt("id")
            number = jsonObject.optString("number")
            currency_code = jsonObject.optString("currency_code")
            user = jsonObject.optString("user")
            balance = jsonObject.optString("balance")
            balance_decode = jsonObject.optBigDecimal("balance_decode").toString()
            pending_balance = jsonObject.optBigDecimal("pending_balance").toString()
            checksum = jsonObject.optString("checksum")
            status = jsonObject.optInt("status")
            locked = jsonObject.optInt("locked")
            created_at = jsonObject.optString("created_at")
            updated_at = jsonObject.optString("updated_at")

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(number)
        parcel.writeString(currency_code)
        parcel.writeString(user)
        parcel.writeString(balance)
        parcel.writeString(balance_decode)
        parcel.writeString(pending_balance)
        parcel.writeString(checksum)
        parcel.writeValue(status)
        parcel.writeValue(locked)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wallet> {
        override fun createFromParcel(parcel: Parcel): Wallet {
            return Wallet(parcel)
        }

        override fun newArray(size: Int): Array<Wallet?> {
            return arrayOfNulls(size)
        }
    }


}

