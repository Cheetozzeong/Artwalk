package com.a401.artwalk.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.usecase.PostIdTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postIdToken: PostIdTokenUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _kakaoLoginButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val kakaoLoginButtonEvent: LiveData<Unit> = _kakaoLoginButtonEvent


    fun saveKakaoIdToken(idToken: String) {
        viewModelScope.launch {
            postIdToken(idToken)
        }
    }

    fun onClickKakaoLoginButton(){
        _kakaoLoginButtonEvent.value = Unit

    }
}
