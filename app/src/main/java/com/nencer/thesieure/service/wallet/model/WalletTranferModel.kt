package com.nencer.thesieure.service.wallet.model

import androidx.databinding.ObservableField

data class WalletTranferModel(val acc : ObservableField<String> = ObservableField(""),
                              val name : ObservableField<String> = ObservableField(""),
                              val amount : ObservableField<String> = ObservableField(""),
                              val msg : ObservableField<String> = ObservableField(""))