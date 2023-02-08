package com.a401.artwalk.view.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.User
import com.a401.domain.usecase.GetUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserpageViewModel @Inject constructor(
    private val getUserInfo: GetUserInfo,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private var _userInfo = MutableStateFlow(User())
    val userInfo: StateFlow<User> = _userInfo

    init {
        setUserInfo()
    }

    private fun setUserInfo() {
        viewModelScope.launch {
            getUserInfo()
                .onStart{}
                .onCompletion{}
                .collect { user ->
                _userInfo.value = user
            }
        }
    }
}