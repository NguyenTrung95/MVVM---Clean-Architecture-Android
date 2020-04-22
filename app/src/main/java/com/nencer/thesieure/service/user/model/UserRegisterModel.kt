package com.nencer.thesieure.service.user.model

import androidx.databinding.ObservableField

data class UserRegisterModel (
    val name: ObservableField<String> = ObservableField(""),
    val username: ObservableField<String> = ObservableField(""),
    val phone_email: ObservableField<String> = ObservableField(""),
    val password: ObservableField<String> = ObservableField("")
)