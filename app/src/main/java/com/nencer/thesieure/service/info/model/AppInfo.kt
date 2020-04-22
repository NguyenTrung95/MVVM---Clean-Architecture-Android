package com.nencer.thesieure.service.info.model

import com.google.gson.annotations.SerializedName

data class AppInfo(
    @SerializedName("url") var url: String? = "",
    @SerializedName("settings") var settings: Setting? = null
)

data class Setting(
    @SerializedName("backendlogo") var backendlogo: String? = "",
    @SerializedName("name") var name: String? = "",
    @SerializedName("phone") var phone: String? = "",
    @SerializedName("email") var email: String? = "",
    @SerializedName("logo") var logo: String? = "",
    @SerializedName("hotline") var hotline: String? = "",
    @SerializedName("copyright") var copyright: String? = "",
    @SerializedName("globalpopup_mes") var globalpopup_mes: String? = ""
)

data class News(
    @SerializedName("current_page") var current_page: String? = "",
    @SerializedName("data") var data: MutableList<NewsData>? = null
)

data class NewsData(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("title") var title: String? = "",
    @SerializedName("news_slug") var news_slug: String? = "",
    @SerializedName("language") var language: String? = "",
    @SerializedName("view_count") var view_count: String? = "",
    @SerializedName("image") var image: String? = "",
    @SerializedName("created_at") var created_at: String? = ""
)

data class NewsDetail(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("title") var title: String? = "",
    @SerializedName("news_slug") var news_slug: String? = "",
    @SerializedName("short_description") var short_description: String? = "",
    @SerializedName("content") var content: String? = "",
    @SerializedName("author") var author: String? = "",
    @SerializedName("author_email") var author_email: String? = "",
    @SerializedName("image") var image: String? = "",
    @SerializedName("created_at") var created_at: String? = "",
    @SerializedName("updated_at") var updated_at: String? = ""
)