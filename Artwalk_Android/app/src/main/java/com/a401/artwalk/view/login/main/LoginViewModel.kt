package com.a401.artwalk.view.login.main

import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.usecase.PostIdTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postIdToken: PostIdTokenUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    fun isSuccessKakaoLogin(idToken: String): Boolean {

        onIo {
            postIdToken(idToken)
            // TODO: 위 작업이 끝난 후 넘어가도록 처리 필요,,,
            delay(1000)
        }

        // TODO: 위 결과값을 반환하도록 해야함
        return true
    }
}
