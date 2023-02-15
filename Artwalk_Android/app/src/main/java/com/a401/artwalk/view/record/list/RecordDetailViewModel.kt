package com.a401.artwalk.view.record.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.BuildConfig
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.RecordForPut
import com.a401.domain.usecase.DeleteRecordUseCase
import com.a401.domain.usecase.GetEditLinkUseCase
import com.a401.domain.usecase.PutRecordTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getEditLink: GetEditLinkUseCase,
    private val deleteRecord: DeleteRecordUseCase,
    private val putRecordTitle: PutRecordTitle

) : BaseViewModel(dispatcherProvider) {

    suspend fun getLink(recordId: Int): String = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch {
            getEditLink(recordId).collectLatest {
                val editUrl = BuildConfig.SHARING_BASE_URL + it.message
                continuation.resume(editUrl,null)
            }
        }
    }

    fun recordDelete(recordId: Int) {
        viewModelScope.launch {
            deleteRecord(recordId).collectLatest {}
        }
    }

    fun editTitle(recordForPut: RecordForPut){
        viewModelScope.launch {
            putRecordTitle(recordForPut).collectLatest {}
        }
    }

}