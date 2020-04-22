package com.nencer.thesieure.ui.main.home.model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString

class CardDataResponse() {
    var items: MutableList<Item> = mutableListOf()
    constructor(jsonArray: JsonArray):this(){
        val results :MutableList<Item> = mutableListOf()
        jsonArray.map{ jsonElement -> jsonElement.asJsonObject }.map{
            results.add(Item(it))
        }
        items.addAll(results)
    }

}

class Item() {
    var id: Int?=0
    var name: String?=""
    var service_code: String?=""
    var image: String?=""
    var short_description: String?=""
    var description: String?=""
    @Transient
    var isSeleted = false

    constructor(jsonObject: JsonObject): this(){
        id = jsonObject.optInt("id")
        name = jsonObject.optString("name")
        service_code = jsonObject.optString("service_code")
        image = jsonObject.optString("image")
        short_description = jsonObject.optString("short_description")
        description = jsonObject.optString("description")

    }

}