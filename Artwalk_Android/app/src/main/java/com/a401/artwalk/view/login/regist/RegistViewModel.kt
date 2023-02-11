package com.a401.artwalk.view.login.regist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.User
import com.a401.domain.usecase.PostRegistArtWalk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val postRegistArtWalk: PostRegistArtWalk
) : BaseViewModel(dispatcherProvider) {

    private var _registFailMassage: MutableLiveData<String> = MutableLiveData()
    val registFailMassage: LiveData<String> = _registFailMassage

    private var _isRegistSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRegistSuccess: LiveData<Boolean> = _isRegistSuccess

    val userId: MutableLiveData<String> = MutableLiveData()
    val userPassword: MutableLiveData<String> = MutableLiveData()
    val userNickName: MutableLiveData<String> = MutableLiveData()


    fun postRegistEvent() {
        if(userId.value != null && userPassword.value != null && userNickName.value != null) {

            viewModelScope.launch {
                postRegistArtWalk(
                    User(
                        userId = userId.value!!,
                        nickName = userNickName.value!!,
                        exp = 0,
                        level = 0,
                        numOfRoute = 0,
                        numOfRecord = 0
                    ),
                    userPassword.value!!
                )
                    .onStart { /* TODO: showLoading */ }
                    .onCompletion { /* TODO: hideLoading */ }
                    .catch { it.printStackTrace() }
                    .collect { result ->
                        when(result) {
                            "SUCCESS" -> {
                                _isRegistSuccess.value = true
                            }
                            "FAIL" -> _registFailMassage.value = "회원가입 실패 (죄송합니다... 아이디를 다른거로 해보세요)"
                        }
                    }
            }
        }
    }
}