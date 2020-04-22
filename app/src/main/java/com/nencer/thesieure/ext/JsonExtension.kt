package com.nencer.thesieure.ext

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.math.BigDecimal

fun JsonObject.optJsonObject(key:String) : JsonObject? {
    return try { get(key).asJsonObject } catch (e:Exception) { null }
}

fun JsonObject.optJsonArray(key:String) : JsonArray? {
    return try { get(key).asJsonArray } catch (e:Exception) { null }
}

fun JsonObject.optInt(key:String, fallback:Int = 0) : Int {
    return try { get(key).asInt } catch (e:Exception) { fallback }
}

fun JsonObject.optLong(key:String, fallback:Long = 0) : Long {
    return try { get(key).asLong } catch (e:Exception) { fallback }
}

fun JsonObject.optString(key:String, fallback:String = "") : String {
    return try { get(key).asString } catch (e:Exception) { fallback }
}

fun JsonObject.optBoolean(key:String, fallback:Boolean = false) : Boolean{
    return try { get(key).asBoolean }catch (e : Exception){ fallback }
}

fun JsonObject.optBigDecimal(key: String, defValue: BigDecimal = BigDecimal.ZERO): BigDecimal {
    return try {
        get(key).asBigDecimal
    } catch (cause: Throwable) {
        defValue
    }
}