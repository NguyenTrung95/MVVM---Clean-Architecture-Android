package com.nencer.thesieure.data.http.interceptor
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.base.constant.Constant
import com.nencer.thesieure.ext.isPayment
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        return chain.proceed(request.newBuilder().apply {
            when {
                isPayment() -> {
                    val auth = "Bearer ${Settings.Account.token}"
                    addHeader("Content-Type", "application/json")
                    addHeader("Accept","application/json")
                    addHeader(Constant.AUTHORIZATION, auth)
                }
                else -> {
                    addHeader("Content-Type", "application/json")
                    addHeader("Accept","application/json")
                }
            }

        }.build())

    }
}