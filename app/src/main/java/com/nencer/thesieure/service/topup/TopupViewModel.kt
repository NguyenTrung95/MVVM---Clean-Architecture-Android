package com.nencer.thesieure.service.topup

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.service.topup.model.TopupHistoryResponse
import com.nencer.thesieure.service.topup.repository.TopupRepository

class TopupViewModel(private val mInfoRepository: TopupRepository) : BaseViewModel() {
    //*mtopuphistory*//*
    fun mtopuphistory(page: Int): MutableLiveData<TopupHistoryResponse> {
        val mutableLiveData = MutableLiveData<TopupHistoryResponse>()

        launch {
            mutableLiveData.value = TopupHistoryResponse(mInfoRepository.mtopuphistory(page))
        }
        return  mutableLiveData
    }

    //*mtopuplist*//*
    fun mtopuplist(): MutableLiveData<JsonArray> {
        val mutableLiveData = MutableLiveData<JsonArray>()

        launch {
            mutableLiveData.value = mInfoRepository.mtopuplist()
        }
        return  mutableLiveData
    }

    //*mtopupprice*//*
    fun mtopupprice(telco_key: String,
                         card_value: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mInfoRepository.mtopupprice(telco_key,card_value)
        }
        return  mutableLiveData
    }

    //*postmtopup--------------------------------------------*//*
    fun postmtopup(  amount: String,
                     phone: String,
                     service_code: String,
                     client_note: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mInfoRepository.postmtopup(amount, phone, service_code, client_note)
        }
        return  mutableLiveData
    }



}
