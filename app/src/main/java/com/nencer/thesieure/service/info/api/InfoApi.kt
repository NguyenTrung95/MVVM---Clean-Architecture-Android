package com.nencer.thesieure.service.info.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.service.info.model.AppInfo
import com.nencer.thesieure.service.info.model.News
import com.nencer.thesieure.service.info.model.NewsDetail

import retrofit2.http.*

interface InfoApi {

    @GET("api/apps/getdefault")
    suspend fun getDefault(
        @Query("MOB_KEY") mode: String
    ): AppInfo


    @GET("api/apps/getmenu")
    suspend fun getMenu(
        @Query("MOB_KEY") mode: String
    ): JsonObject


    @GET("api/apps/getsliders")
    suspend fun getSliders(
        @Query("MOB_KEY") mode: String
    ): JsonArray


    @GET("api/apps/getlistnews")
    suspend fun getlistnews(
        @Query("MOB_KEY") mode: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int): News


    @GET("api/apps/getnewsdetail")
    suspend fun getnewsDetail(
        @Query("MOB_KEY") mode: String,
        @Query("news_id") user_id: Int): NewsDetail

    @GET("api/apps/getcountrylist")
    suspend fun getCountry_code(
        @Query("MOB_KEY") mode: String): JsonArray



}