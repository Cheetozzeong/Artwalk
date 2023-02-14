package com.a401.artwalk.view.record.draw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.RecordForSave
import com.a401.domain.usecase.PostRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val postRecord: PostRecordUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider){

    private var _totalDuration: MutableLiveData<Int> = MutableLiveData(0)
    val totalDuration: LiveData<Int> = _totalDuration

    private val _distance: MutableLiveData<Double> = MutableLiveData(0.0)
    val distance: LiveData<Double> = _distance

    private val _title: MutableLiveData<String> = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _isSuccessSave: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSuccessSave: LiveData<Boolean> = _isSuccessSave

    private val _msg: MutableLiveData<String> = MutableLiveData()
    val msg: LiveData<String> = _msg

    fun saveRecord(polyline: String) {

        if(title.value == null) {

        }else {
            viewModelScope.launch {
                postRecord(
                    RecordForSave(
                        duration = totalDuration.value ?: 0,
                        distance = distance.value ?: 0.0,
                        detail = title.value,
                        polyline
                    )
                )
                    .onStart {  }
                    .onCompletion {  }
                    .collect() { result ->
                        if(result == "SUCCESS") {
                            _isSuccessSave.value = true
                            _msg.value = "저장 성공!!"
                        }else {
                            _msg.value = "저장 실패!!"
                        }
                    }
            }
        }
    }

    fun setTitle(title: String) {
        _title.value = title
    }

}
