package com.a401.artwalk.view.record.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.usecase.GetEditLinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getEditLink: GetEditLinkUseCase,
    private val deleteRecord: DeleteRecordUseCase
) : BaseViewModel(dispatcherProvider) {

    private val _totalDuration: MutableLiveData<Int> = MutableLiveData(0)

    private val _distance: MutableLiveData<Double> = MutableLiveData(0.0)

    private val _detail: MutableLiveData<String> = MutableLiveData("")

    suspend fun getLink(recordId: Int): String = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch {
            getEditLink(recordId).collectLatest {
                val editUrl = BuildConfig.SHARING_BASE_URL + it.message
                continuation.resume(editUrl,null)
            }
        }
    }

    fun deleteRecord(recordId: Int) {
        viewModelScope.launch {
            deleteRecord(recordId)
        }
    }
}