package com.a401.artwalk.view.user

import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.artwalk.view.record.list.RecordListItem
import com.a401.domain.model.RecordForList
import com.a401.domain.model.User
import com.a401.domain.usecase.FetchRecordList
import com.a401.domain.usecase.GetUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserpageViewModel @Inject constructor(
    private val getUserInfo: GetUserInfo,
    dispatcherProvider: DispatcherProvider,
    private val fetchRecordList: FetchRecordList
) : BaseViewModel(dispatcherProvider) {

    private val recordList = MutableStateFlow<List<RecordForList>>(emptyList())
    private var _userInfo = MutableStateFlow(User())
    val userInfo: StateFlow<User> = _userInfo
    val records: StateFlow<List<RecordListItem>> = recordList.map { routeForListList ->
        routeForListList.map { recordForList ->
            RecordListItem(recordForList)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    init {
        setUserInfo()
        getRecords()
    }

    private fun setUserInfo() {
        viewModelScope.launch {
            getUserInfo()
                .onStart {}
                .onCompletion {}
                .collect { user ->
                    _userInfo.value = user
                }
        }
    }

    fun getRecords() {
        viewModelScope.launch {
            fetchRecordList().collectLatest {
                recordList.value = it
            }
        }
    }

}