package com.nencer.thesieure.service.info

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.service.info.repository.InfoRepository
import com.nencer.thesieure.service.info.model.AppInfo
import com.nencer.thesieure.service.info.model.CountryResponse
import com.nencer.thesieure.service.info.model.News
import com.nencer.thesieure.service.info.model.NewsDetail

class InfoViewModel(private val mInfoRepository: InfoRepository) : BaseViewModel() {
    /*getDefault*/
    fun getDefault(): MutableLiveData<AppInfo> {
        val mutableLiveData = MutableLiveData<AppInfo>()

        launch {
            mutableLiveData.value = mInfoRepository.getDefault()
        }
        return  mutableLiveData
    }

    /*getMenu*/
    fun getMenu(): MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mInfoRepository.getMenu()
        }
        return  mutableLiveData
    }

    /*getSliders*/
    fun getSliders():MutableLiveData<JsonArray>{
        val mutableLiveData = MutableLiveData<JsonArray>()

        launch {
            mutableLiveData.value = mInfoRepository.getSliders()
        }
        return  mutableLiveData
    }

    /*getlistnews--------------------------------------------*/
    fun getlistnews(page:Int):MutableLiveData<News>{
        val mutableLiveData = MutableLiveData<News>()

        launch {
            mutableLiveData.value = mInfoRepository.getlistnews(page)
        }
        return  mutableLiveData
    }

    /*user info*/
    fun getnewsDetail(newsId: Int):MutableLiveData<NewsDetail>{
        val mutableLiveData = MutableLiveData<NewsDetail>()

        launch {
            mutableLiveData.value = mInfoRepository.getnewsDetail(newsId)
        }
        return  mutableLiveData
    }

    /*user info*/
    fun getCountry():MutableLiveData<CountryResponse>{
        val mutableLiveData = MutableLiveData<CountryResponse>()

        launch {
            mutableLiveData.value = CountryResponse(mInfoRepository.getCountry())
        }
        return  mutableLiveData
    }


}
