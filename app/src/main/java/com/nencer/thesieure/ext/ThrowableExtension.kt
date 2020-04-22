package com.nencer.thesieure.ext
import org.json.JSONObject
import retrofit2.HttpException

/**
 * Read http message and update to message
 */
val Throwable.httpNormalize: Throwable
    get() {
        return (this as? HttpException)?.takeIf { it.response()?.errorBody() != null }?.run {
            val json = JSONObject(response()?.errorBody()!!.string())
            val message = json.optJSONObject("error")?.optString("message") ?: json.toString()
            Throwable(message, this)
        } ?: this
    }
