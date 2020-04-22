package com.nencer.thesieure.service.payment.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.service.payment.response.HistoryResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface PaymentApi {


    @POST("api/apps/buycard")
    suspend fun buyCard(
        @Body body: RequestBody
    ): JsonObject


    @GET("api/apps/allcardproduct")
    suspend fun getAllCards(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String
    ): JsonArray


    @GET("api/apps/getcarditem")
    suspend fun getCardItem(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("softcard_id") softcard_id: Int,
        @Query("api_token") api_token: String
    ): JsonArray


    @POST("api/apps/buycardhistory")
    suspend fun buycardHistory(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): HistoryResponse


}