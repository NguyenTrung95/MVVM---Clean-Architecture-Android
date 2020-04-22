package com.nencer.thesieure.ui.main.home.model

import com.google.gson.annotations.SerializedName

data class Price(@SerializedName("telco_key")
                 var telco_key:String="",
                 @SerializedName("value")
                 var value: String="",
                 @SerializedName("fees")
                 var fees:Int=0,
                 @SerializedName("receive_amount")
                 var receive_amount:Int=0 )