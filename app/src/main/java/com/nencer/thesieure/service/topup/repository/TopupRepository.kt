package com.nencer.thesieure.service.topup.repository

import com.nencer.thesieure.config.Configs
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.service.topup.api.TopupApi

class TopupRepository(private val mApi: TopupApi) {

    /*mtopuphistory*/
    suspend fun mtopuphistory(page: Int) =
        mApi.mtopuphistory(Configs.MODE_KEY, Settings.Account.token, Settings.Account.id, 10, page)

    /*mtopuplist*/
    suspend fun mtopuplist() = mApi.mtopuplist(
        Configs.MODE_KEY,
        Settings.Account.token, Settings.Account.id
    )

    /*mtopupprice*/
    suspend fun mtopupprice(
        telco_key: String,
        card_value: String
    ) = mApi.mtopupprice(
        Configs.MODE_KEY,
        Settings.Account.token, Settings.Account.id, telco_key, card_value
    )

    /*postmtopup*/
    suspend fun postmtopup(
        amount: String,
        phone: String,
        service_code: String,
        client_note: String

    ) = mApi.postmtopup(
        Configs.MODE_KEY,
        Settings.Account.token,
        Settings.Account.id,
        amount,
        phone,
        service_code,
        client_note
    )


}
