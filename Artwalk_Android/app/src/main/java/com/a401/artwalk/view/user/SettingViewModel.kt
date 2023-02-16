package com.a401.artwalk.view.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.usecase.RemoveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val removeUserUseCase: RemoveUserUseCase,
    dispatcherProvider: DispatcherProvider,
) : BaseViewModel(dispatcherProvider) {

    private val _isSuccessRemoveUser: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessRemoveUser: LiveData<Boolean> = _isSuccessRemoveUser

    fun removeUser() {
        viewModelScope.launch {
            removeUserUseCase().onCompletion{  }.collect { result ->
                _isSuccessRemoveUser.value = result == "SUCCESS"
            }
        }
    }
}
