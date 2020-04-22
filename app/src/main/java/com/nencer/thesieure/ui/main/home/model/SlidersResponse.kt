package com.nencer.thesieure.ui.main.home.model

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nencer.thesieure.ext.optInt
import com.nencer.thesieure.ext.optString

class SlidersResponse() {
    var items: MutableList<ItemInfo> = mutableListOf()
    constructor(jsonArray: JsonArray):this(){
        val results :MutableList<ItemInfo> = mutableListOf()
        jsonArray.map{ jsonElement -> jsonElement.asJsonObject }.map{
            results.add(ItemInfo(it))
        }
        items.addAll(results)
    }

}

class ItemInfo() {
    var slider_name: String?=""
    var slider_image: String?=""
    var slider_text: String?=""
    var slider_url: String?=""
    var sort_order: Int?=0

    constructor(jsonObject: JsonObject): this(){
        slider_name = jsonObject.optString("slider_name")
        slider_image = jsonObject.optString("slider_image")
        slider_text = jsonObject.optString("slider_text")
        slider_url = jsonObject.optString("slider_url")
        sort_order = jsonObject.optInt("sort_order")
    }

}