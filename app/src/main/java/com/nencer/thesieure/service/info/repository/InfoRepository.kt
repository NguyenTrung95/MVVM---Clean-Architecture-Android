package com.nencer.thesieure.service.info.repository

import com.nencer.thesieure.config.Configs
import com.nencer.thesieure.service.info.api.InfoApi

class InfoRepository(private val mInfoApi: InfoApi) {

    /*getDefault*/
    suspend fun getDefault() = mInfoApi.getDefault(Configs.MODE_KEY)

    /*getMenu*/
    suspend fun getMenu() = mInfoApi.getMenu(Configs.MODE_KEY)

    /*getSliders*/
    suspend fun getSliders() = mInfoApi.getSliders(Configs.MODE_KEY)

    /*getlistnews*/
    suspend fun getlistnews(page:Int) = mInfoApi.getlistnews(Configs.MODE_KEY,5,page)

    /*getnewsDetail*/
    suspend fun getnewsDetail(newId: Int) = mInfoApi.getnewsDetail(Configs.MODE_KEY,newId)

    /*getCountry*/
    suspend fun getCountry() = mInfoApi.getCountry_code(Configs.MODE_KEY)

}
