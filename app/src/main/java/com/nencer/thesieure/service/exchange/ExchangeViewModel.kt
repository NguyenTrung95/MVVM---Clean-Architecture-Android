package com.nencer.thesieure.service.exchange

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.service.exchange.repository.ExchangeRepository
import com.nencer.thesieure.service.exchange.response.HistoryChargingResponse
import com.nencer.thesieure.ui.main.home.model.Price

class ExchangeViewModel(private val mInfoRepository: ExchangeRepository) : BaseViewModel() {
    /*charginglistcard*/
    fun charginglistcard(): MutableLiveData<JsonArray> {
        val mutableLiveData = MutableLiveData<JsonArray>()

        launch {
            mutableLiveData.value = mInfoRepository.charginglistcard()
        }
        return  mutableLiveData
    }

    /*getMenu*/
    fun chargingcardprice(telco_key: String, cardValue: String): MutableLiveData<Price> {
        val mutableLiveData = MutableLiveData<Price>()

        launch {
            mutableLiveData.value = mInfoRepository.chargingcardprice(telco_key,cardValue)
        }
        return  mutableLiveData
    }

    /*getSliders*/
    fun postchargingcard(telco_key: String, card_serial: String, card_code: String,
                         card_value: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mInfoRepository.postchargingcard(telco_key,card_serial,card_code,card_value)
        }
        return  mutableLiveData
    }

    /*getlistnews--------------------------------------------*/
    fun charginghistory( per_page: Int,
                         page: Int):MutableLiveData<HistoryChargingResponse>{
        val mutableLiveData = MutableLiveData<HistoryChargingResponse>()

        launch {
            mutableLiveData.value = mInfoRepository.charginghistory(per_page, page)
        }
        return  mutableLiveData
    }



}
