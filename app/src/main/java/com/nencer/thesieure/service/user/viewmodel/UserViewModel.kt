package com.nencer.thesieure.service.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.nencer.thesieure.base.viewmodel.BaseViewModel
import com.nencer.thesieure.config.Configs
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.ext.optString
import com.nencer.thesieure.ext.otherwise
import com.nencer.thesieure.ext.yes
import com.nencer.thesieure.service.user.model.response.*
//import com.nencer.nencerwallet.service.user.model.response.LoginResponse
//import com.nencer.nencerwallet.service.user.model.response.RegisterResponse
import com.nencer.thesieure.service.user.repository.UserRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UserViewModel(private val mUserRepository: UserRepository) : BaseViewModel() {
    /*login*/
    fun login(userName: String,password: String): MutableLiveData<Any> {
        val mutableLiveData = MutableLiveData<Any>()

        launch {
            val response = mUserRepository.login(userName,password)
            when(response.optString("status")){
                "success" -> mutableLiveData.value = LoginResponse(response)
                "need_verify" -> mutableLiveData.value = VerifyResponse(response)
                else -> mutableLiveData.value ="No response"
            }

        }
        return  mutableLiveData
    }

    /*logout*/
    fun logout(user_id: String,api_token: String): MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.logout(user_id,api_token)
        }
        return  mutableLiveData
    }

    /*register*/
    fun register(name: String,userName: String,phone_Email: String,password: String):MutableLiveData<Any>{
        val mutableLiveData = MutableLiveData<Any>()

        launch {
            val data = mUserRepository.register(name,userName,phone_Email,password)

            data.optString("error_code").isNotEmpty().yes {
                val msg = data.optString("message")
                mutableLiveData.value = msg
            }.otherwise {
                mutableLiveData.value = RegisterResponse(data)
            }
        }
        return  mutableLiveData
    }

    /*verify--------------------------------------------*/
    fun verify(phone: String,token: String,otp: String,sessionId: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.verify(phone,token,otp,sessionId)
        }
        return  mutableLiveData
    }

    /*user info*/
    fun getUserInfo(userId: String,apiToken: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.getUserInfo(userId,apiToken)
        }
        return  mutableLiveData
    }

    /*update*/
    fun update(fields:List<Pair<String,String>>):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()
        val json = JSONObject()
        json.putOpt("MOB_KEY", Configs.MODE_KEY)
        json.putOpt("user_id", Settings.Account.id)

        val data = JSONObject()

        for (field in fields){
            data.putOpt(field.first,field.second)
        }

        json.putOpt("data",data)

        val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
        launch {
            mutableLiveData.value = mUserRepository.update(body)
        }
        return  mutableLiveData
    }

    /*get Name*/
    fun getName(apiToken: String,userName: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.getName(apiToken,userName)
        }
        return  mutableLiveData
    }

    /*getFieldUpdateActive*/
    fun getFieldUpdateActive():MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.getFieldUpdateActive()
        }
        return  mutableLiveData
    }

    /*update*/
    fun changePassword(userId: String,apiToken: String,oldPassword: String,newPassword: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.changePassword(userId,apiToken,oldPassword,newPassword)
        }
        return  mutableLiveData
    }

    /*get Name*/
    fun getListVerify(userId: String,apiToken: String):MutableLiveData<OTPs>{
        val mutableLiveData = MutableLiveData<OTPs>()

        launch {
            mutableLiveData.value =OTPs(mUserRepository.getListVerify(userId,apiToken))
        }
        return  mutableLiveData
    }

    /*get Name*/
    fun forgot(username: String):MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value =mUserRepository.forgot(username)
        }
        return  mutableLiveData
    }

    fun forgotVerify(
        username: String,
        type: String,
        tmp_token: String,
        new_password: String,
        verify_code: String) :MutableLiveData<JsonObject>{
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value =mUserRepository.forgotVerify(username,type,
                tmp_token,
                new_password,
                verify_code)
        }
        return  mutableLiveData
    }

    /*update version*/
    fun updateVersion(currentVersion: String): MutableLiveData<JsonObject> {
        val mutableLiveData = MutableLiveData<JsonObject>()

        launch {
            mutableLiveData.value = mUserRepository.updateVersion(currentVersion)
        }
        return  mutableLiveData
    }
}
