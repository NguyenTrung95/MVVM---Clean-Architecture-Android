package com.nencer.thesieure.service.payment.repository

import com.nencer.thesieure.config.Configs
import com.nencer.thesieure.service.payment.api.PaymentApi
import okhttp3.RequestBody

class PaymentRepository(private val paymentApi: PaymentApi) {
    /*buy card*/
    suspend fun buyCard(
        requestBody: RequestBody
    ) = paymentApi.buyCard(requestBody)

    /*buy card*/
    suspend fun paymentHistory(
        user_id: String,
        api_token: String,
        per_page: Int,
        page: Int
    ) = paymentApi.buycardHistory(Configs.MODE_KEY, user_id, api_token, per_page, page)

    /*get card item*/
    suspend fun getCardItem(
        user_id: String,
        softcard_id: Int,
        api_token: String
    ) = paymentApi.getCardItem(Configs.MODE_KEY, user_id, softcard_id, api_token)

    /*get all card*/
    suspend fun getAllCard(
        user_id: String,
        api_token: String
    ) = paymentApi.getAllCards(Configs.MODE_KEY, api_token, user_id)



}