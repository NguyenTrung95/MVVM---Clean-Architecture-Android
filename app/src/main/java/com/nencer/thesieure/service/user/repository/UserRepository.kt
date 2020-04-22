package com.nencer.thesieure.service.user.repository

import com.nencer.thesieure.config.Configs
import com.nencer.thesieure.config.Settings
import com.nencer.thesieure.helper.DeviceUtil
import com.nencer.thesieure.service.user.api.UserApi
import okhttp3.RequestBody

class UserRepository(private val mUserApi: UserApi) {

    /*login*/
    suspend fun login(userName: String, password: String) = mUserApi.login(
        Configs.MODE_KEY,
        userName,
        password,
        Configs.REMEMBER.YES.status,
        DeviceUtil.deviceID()
    )

    /*logout*/
    suspend fun logout(user_id: String, api_token: String) =
        mUserApi.logout(Configs.MODE_KEY, user_id, api_token)

    /*register*/
    suspend fun register(name: String, userName: String, phone_Email: String, password: String) =
        mUserApi.register(Configs.MODE_KEY, name, userName, phone_Email, password)

    /*user info*/
    suspend fun getUserInfo(user_id: String, api_token: String) =
        mUserApi.getInfo(Configs.MODE_KEY, user_id, api_token)

    /*verify*/
    suspend fun verify(
        phone: String,
        token: String, otp: String,
        session_id: String
    ) = mUserApi.verify(
        Configs.MODE_KEY, phone, Configs.REMEMBER.YES.status,
        DeviceUtil.deviceID(), token, otp, session_id
    )

    /*update*/
    suspend fun update(body: RequestBody
    ) = mUserApi.update(body)

    suspend fun getFieldUpdateActive() = mUserApi.fieldUpdateActive(Configs.MODE_KEY, Settings.Account.id, Settings.Account.token)


    /*change password*/
    suspend fun changePassword(
        user_id: String,
        api_token: String, old_password: String,
        new_password: String
    ) = mUserApi.changePassword(Configs.MODE_KEY, user_id, api_token, old_password, new_password)

    /*get name*/
    suspend fun getName(
        api_token: String, username: String
    ) = mUserApi.getName(Configs.MODE_KEY, api_token, username)

    /*get name*/
    suspend fun getListVerify(
        userId: String,
        api_token: String
    ) = mUserApi.getListVerify(Configs.MODE_KEY,userId, api_token)

    /*get name*/
    suspend fun forgot(
        username: String
    ) = mUserApi.forgotPassword(Configs.MODE_KEY,username)

    suspend fun forgotVerify(
        username: String,
        type: String,
        tmp_token: String,
        new_password: String,
        verify_code: String
    ) = mUserApi.userpasswordverify(Configs.MODE_KEY,type, tmp_token , new_password , verify_code,username)

    suspend fun updateVersion(current_version: String) = mUserApi.updateVersion(Configs.MODE_KEY, Settings.Account.token, current_version, Settings.Account.id)


}
