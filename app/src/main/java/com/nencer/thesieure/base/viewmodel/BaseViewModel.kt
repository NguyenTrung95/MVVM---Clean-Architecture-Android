package com.nencer.thesieure.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val mStateLiveData = MutableLiveData<StateActionEvent>()
    val errorMsg = MutableLiveData<String>()
    fun launch(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch {
            try {
                mStateLiveData.value = LoadState
                block()
                mStateLiveData.value = SuccessState
            } catch (e: Exception) {
                mStateLiveData.value = ErrorState(e.message)
                errorMsg.value = e.message
            }
        }

    }

}