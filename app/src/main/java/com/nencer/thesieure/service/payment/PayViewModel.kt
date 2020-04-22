package com.nencer.thesieure.service.payment

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Configs
import com.nencer.thesieure.ui.main.home.model.CardDataResponse
import com.nencer.thesieure.service.payment.repository.PaymentRepository
import com.nencer.thesieure.service.payment.response.Card
import com.nencer.thesieure.service.payment.response.HistoryResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class PayViewModel(val repository: PaymentRepository) : BaseViewModel() {
    val liscard = MutableLiveData<JsonArray>()

    fun buyCard(user_id: String,
                listCard: MutableList<Card>): MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()
        val json = JSONObject()
        json.putOpt("MOB_KEY", Configs.MODE_KEY)
        json.putOpt("user_id", user_id)

        val cards = JSONArray()

        for (card in listCard){
            val order = JSONObject()

            order.putOpt("service_code",card.service_code)
            order.putOpt("value",card.value)
            order.putOpt("qty",card.count)

            cards.put(order)
        }

        json.putOpt("order",cards)

        val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        launch {
            mutableLiveData.value = repository.buyCard(body)
        }
        return mutableLiveData
    }

    fun getAllCard(user_id: String,
                api_token: String): MutableLiveData<CardDataResponse>{
        val mutableLiveData = MutableLiveData<CardDataResponse>()
        launch {
            mutableLiveData.value = CardDataResponse(
                repository.getAllCard(
                    user_id,
                    api_token
                )
            )
        }
        return mutableLiveData
    }

    fun getCardItems(user_id: String,
                     api_token: String,
                     softcard_id: Int): MutableLiveData<JsonArray>{
        launch {
            liscard.value = repository.getCardItem(user_id, softcard_id,api_token)
        }
        return liscard
    }

    fun paymentHistory(user_id: String,
                   api_token: String,
                   page:Int): MutableLiveData<HistoryResponse>{
        val mutableLiveData = MutableLiveData<HistoryResponse>()
        launch {
            mutableLiveData.value = repository.paymentHistory(user_id, api_token,10,page)
        }
        return mutableLiveData
    }


}