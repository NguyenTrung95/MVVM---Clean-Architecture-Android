package com.nencer.thesieure.service.wallet.repository

import com.nencer.thesieure.config.Configs
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.service.wallet.api.WalletApi

class WalletRepository(private val mWalletApi: WalletApi) {

    /*tranfer*/
    suspend fun tranfer(userName: String, amount: String,msg: String) = mWalletApi.tranfer(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        userName,
        amount,
        msg
    )

    suspend fun getListPaygate(type :String) = mWalletApi.getListPaygate(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        type
    )


    suspend fun postdepositwallet(amount :String , paygate_id : String) = mWalletApi.postdepositwallet(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        Settings.Account.userName,
        amount,
        paygate_id
    )

    suspend fun history(type : String , per_page : Int , page : Int) = mWalletApi.history(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        type,
        per_page,
        page
    )
    suspend fun getListBankUser(filter :String) = mWalletApi.getListBankUser(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        filter
    )
    suspend fun postwithdrawwallet(amount :String , bank_id : String , description: String) = mWalletApi.postwithdrawwallet(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        Settings.Account.userName,
        amount,
        bank_id,
        description
    )

    suspend fun getListLocalBank() = mWalletApi.getListLocalBank(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token
    )
    suspend fun storebankuser(paygate_code :String , account_name : String , account_number: String, account_card_number: String, account_branch: String) = mWalletApi.storebankuser(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        paygate_code,
        account_name,
        account_number,
        account_card_number,
        account_branch
    )
    suspend fun deletebankuser(bank_id : String) = mWalletApi.deletebankuser(
        Configs.MODE_KEY,
        Settings.Account.id,
        Settings.Account.token,
        bank_id
    )

}
