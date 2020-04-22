package com.nencer.thesieure.service.wallet.response

class TypeHistory(){
    var code: Int?= 0
    var name: String?=""
    constructor(mCode : Int? , mName : String?) : this() {
        code = mCode
        name = mName
    }
    override fun toString(): String {
        return name.toString()
    }
}