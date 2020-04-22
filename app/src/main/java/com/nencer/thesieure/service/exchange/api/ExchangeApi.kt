package com.nencer.thesieure.service.exchange.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.service.exchange.response.HistoryChargingResponse
import com.nencer.thesieure.ui.main.home.model.Price

import retrofit2.http.*

interface ExchangeApi {

    @GET("api/apps/charginglistcard")
    suspend fun charginglistcard(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String
    ): JsonArray


    @GET("api/apps/chargingcardprice")
    suspend fun chargingcardprice(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String,
        @Query("telco_key") telco_key: String,
        @Query("card_value") card_value: String
    ): Price


    @POST("api/apps/postchargingcard")
    suspend fun postchargingcard(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String,
        @Query("telco_key") telco_key: String,
        @Query("card_serial") card_serial: String,
        @Query("card_code") card_code: String,
        @Query("card_value") card_value: String
    ): JsonObject


    @POST("api/apps/charginghistory")
    suspend fun charginghistory(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int): HistoryChargingResponse


}