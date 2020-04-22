package com.nencer.thesieure.service.wallet.api
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.service.wallet.response.HistoryDepositResponse
import retrofit2.http.*

interface WalletApi {

    @POST("api/apps/wallethistory")
    suspend fun history(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") id: String,
        @Query("api_token") password: String,
        @Query("type") type: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): HistoryDepositResponse




    @POST("api/apps/wallettransfer")
    suspend fun tranfer(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("username") username: String,
        @Query("amount") amount: String,
        @Query("description") description: String): JsonObject

    // lay danh sach cong thanh toan
    @GET("api/apps/listpaygate")
    suspend fun getListPaygate(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("type") type: String
    ) : JsonArray

    // nap tien
    @POST("api/apps/postdepositwallet")
    suspend fun postdepositwallet(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("username") username: String,
        @Query("amount") amount: String,
        @Query("paygate_id") paygate_id: String): JsonObject
    // lay danh sach bank

    @GET("api/apps/listbankuser")
    suspend fun getListBankUser(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("filter") filter: String
    ) : JsonArray


    @POST("api/apps/postwithdrawwallet")
    suspend fun postwithdrawwallet(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("username") username: String,
        @Query("amount") amount: String,
        @Query("bank_id") bank_id: String,
        @Query("description") description: String): JsonObject

    @GET("api/apps/listlocalbank")
    suspend fun getListLocalBank(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String) : JsonArray


    @POST("api/apps/storebankuser")
    suspend fun storebankuser(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("paygate_code") paygate_code: String,
        @Query("account_name") account_name: String,
        @Query("account_number") account_number: String,
        @Query("account_card_number") account_card_number: String,
        @Query("account_branch") account_branch: String): JsonObject

    @POST("api/apps/deletebankuser")
    suspend fun deletebankuser(
        @Query("MOB_KEY") mode: String,
        @Query("user_id") user_id: String,
        @Query("api_token") api_token: String,
        @Query("bank_id") bank_id: String
      ): JsonObject
}