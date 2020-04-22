package com.nencer.thesieure.service.wallet

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.nencer.thesieure.service.wallet.repository.WalletRepository
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.service.wallet.response.BankUserList
import com.nencer.thesieure.service.wallet.response.HistoryDepositResponse
import com.nencer.thesieure.service.wallet.response.PaygateList

class WalletViewModel(private val mUserRepository: WalletRepository) : BaseViewModel() {
    /*tranfer*/
    fun tranfer(userName: String,amount: String,msg:String): MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
//            val response = mUserRepository.tranfer(userName,amount, msg)
            mutableLiveData.value = mUserRepository.tranfer(userName,amount, msg)
            }

        return  mutableLiveData
    }
    // lay ds cong thanh toan
    fun getListPaygate(type : String) : MutableLiveData<PaygateList>{
        val mutableLiveData = MutableLiveData<PaygateList>()
        launch{
            mutableLiveData.value = PaygateList(mUserRepository.getListPaygate(type))
        }
        return mutableLiveData
    }

    // ham nap tien
    fun postdepositwallet(amount :String , paygate_id : String) : MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.postdepositwallet(amount, paygate_id)
        }
        return mutableLiveData
    }

    // lay lich su giao dich
    fun history(
                   type: String,
                   per_page:Int): MutableLiveData<HistoryDepositResponse>{
        val mutableLiveData = MutableLiveData<HistoryDepositResponse>()
        launch {
            mutableLiveData.value = mUserRepository.history(type, 10,per_page)
        }
        return mutableLiveData
    }

    fun getListBankUser(filter : String) : MutableLiveData<BankUserList>{
        val mutableLiveData = MutableLiveData<BankUserList>()
        launch{
            mutableLiveData.value = BankUserList(mUserRepository.getListBankUser(filter))
        }
        return mutableLiveData
    }

    fun postwithdrawwallet(amount :String , paygate_id : String, des: String) : MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.postwithdrawwallet(amount, paygate_id , des)
        }
        return mutableLiveData
    }

    fun getListLocalBank() : MutableLiveData<BankUserList>{
        val mutableLiveData = MutableLiveData<BankUserList>()
        launch{
            mutableLiveData.value = BankUserList(mUserRepository.getListLocalBank())
        }
        return mutableLiveData
    }

    fun storebankuser(paygate_code :String , account_name : String , account_number: String, account_card_number: String, account_branch: String) : MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.storebankuser(paygate_code, account_name,account_number,account_card_number,account_branch)
        }
        return mutableLiveData
    }

    fun deletebankuser(bank_id : String) : MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.deletebankuser(bank_id)
        }
        return mutableLiveData
    }

}
