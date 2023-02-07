package com.a401.artwalk.view.login.regist

import androidx.lifecycle.MutableLiveData
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val userId: MutableLiveData<String> = MutableLiveData()
    val userPassword: MutableLiveData<String> = MutableLiveData()
    val userNickName: MutableLiveData<String> = MutableLiveData()

    fun postRegistEvent() {
        if(userId.value != null && userPassword.value != null && userNickName.value != null) {
            // TODO: 회원가입
        }
    }
}