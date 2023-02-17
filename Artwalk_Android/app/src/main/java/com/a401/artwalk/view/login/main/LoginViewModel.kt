package com.a401.artwalk.view.login.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.usecase.PostArtWalkLoginUseCase
import com.a401.domain.usecase.PostSocialLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postSocialLogin: PostSocialLoginUseCase,
    private val postArtWalkLogin: PostArtWalkLoginUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private var _isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    val artWalkId: MutableLiveData<String> = MutableLiveData()
    val artWalkPassword: MutableLiveData<String> = MutableLiveData()

    fun sendArtWalkLoginRequest() {
        if(artWalkId.value != null && artWalkPassword.value != null) {
            viewModelScope.launch {
                postArtWalkLogin(artWalkId.value!!, artWalkPassword.value!!)
                    .onStart {  }
                    .onCompletion {  }
                    .collect { result ->
                        when (result) {
                            "SUCCESS" -> {
                                _isLoginSuccess.value = true
                            }
                            "FAIL" -> {

                            }
                        }
                    }
            }
        }
    }

    fun sendKakaoLoginRequest(idToken: String) {
        viewModelScope.launch {
            postSocialLogin(idToken, "kakao")
                .onStart {}
                .onCompletion { }
                .collect { result ->
                    when (result) {
                        "SUCCESS" -> {
                            _isLoginSuccess.value = true
                        }
                        "FAIL" -> {

                        }
                    }
                }
        }
    }
}

