package com.nencer.thesieure.service.topup.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject

import retrofit2.http.*

interface TopupApi {

    @GET("api/apps/mtopuplist")
    suspend fun mtopuplist(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String
    ): JsonArray


    @POST("api/apps/mtopuphistory")
    suspend fun mtopuphistory(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): JsonObject


    @POST("api/apps/mtopupprice")
    suspend fun mtopupprice(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String,
        @Query("telco") telco: String,
        @Query("card_value") card_value: String
    ): JsonObject


    @POST("api/apps/postmtopup")
    suspend fun postmtopup(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("user_id") user_id: String,
        @Query("amount") amount: String,
        @Query("phone") phone: String,
        @Query("service_code") service_code: String,
        @Query("client_note") client_note: String): JsonObject


}