package com.nencer.thesieure.service.user.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.RequestBody
//import com.nencer.nencerwallet.service.user.model.response.LoginResponse
//import com.nencer.nencerwallet.service.user.model.response.RegisterResponse
import retrofit2.http.*

interface UserApi {

    @POST("api/apps/userlogin")
    suspend fun login(
        @Query("MOB_KEY") mode: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("remember") remember: String,
        @Query("device_id") device_id: String
    ): JsonObject


    @POST("api/apps/userlogout")
    suspend fun logout(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String
    ): JsonObject


    @POST("api/apps/userregister")
    suspend fun register(
        @Query("MOB_KEY") mode: String,
        @Query("name") name: String,
        @Query("username") username: String,
        @Query("phone_email") phone_email: String,
        @Query("password") password: String
    ): JsonObject


    @POST("api/apps/userchangepass")
    suspend fun changePassword(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("old_password") old_password: String,
        @Query("new_password") new_password: String): JsonObject


    @POST("api/apps/userupdateconfirm")
    suspend fun update(@Body body: RequestBody): JsonObject


    @POST("api/apps/userinfo")
    suspend fun getInfo(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String): JsonObject


    @POST("api/apps/getname")
    suspend fun getName(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("username") username: String): JsonObject


    @POST("api/apps/userloginverify")
    suspend fun verify(
        @Query("MOB_KEY") mode: String,
        @Query("phone") phone: String,
        @Query("remember") remember: String,
        @Query("device_id") device_id: String,
        @Query("token") token: String,
        @Query("otp") otp: String,
        @Query("session_id") session_id: String): JsonObject



    @POST("api/apps/userotp")
    suspend fun getListVerify(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String): JsonArray

    @POST("api/apps/userresetpassword")
    suspend fun forgotPassword(
        @Query("MOB_KEY") mode: String,
        @Query("username") username: String): JsonObject

    @POST("api/apps/userpasswordverify")
    suspend fun userpasswordverify(
        @Query("MOB_KEY") mode: String,
        @Query("type") type: String,
        @Query("tmp_token") tmp_token: String,
        @Query("new_password") new_password: String,
        @Query("verify_code") verify_code: String,
        @Query("username") username: String): JsonObject

    @GET("api/apps/userupdate")
    suspend fun fieldUpdateActive(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String): JsonObject

    @POST("api/apps/userappversion")
    suspend fun updateVersion(
        @Query("MOB_KEY") mode: String,
        @Query("api_token") api_token: String,
        @Query("current_version") current_version: String,
        @Query("user_id") user_id: String): JsonObject
}