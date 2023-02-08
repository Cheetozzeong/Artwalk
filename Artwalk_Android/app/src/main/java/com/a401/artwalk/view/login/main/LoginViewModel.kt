package com.a401.artwalk.view.login.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.usecase.PostIdTokenUseCase
import com.mapbox.maps.extension.style.expressions.dsl.generated.id
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postIdToken: PostIdTokenUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private var _isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    fun isSuccessKakaoLogin(idToken: String) {

        viewModelScope.launch {
            postIdToken(idToken)
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

