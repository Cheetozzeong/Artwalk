package com.a401.artwalk.view.record.draw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.RecordForSave
import com.a401.domain.usecase.PostRecordUseCase
import com.mapbox.geojson.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timer
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val postRecord: PostRecordUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider){

    private var _totalDuration: MutableLiveData<Int> = MutableLiveData(0)
    val totalDuration: LiveData<Int> = _totalDuration

    private val _distance: MutableLiveData<Double> = MutableLiveData(0.0)
    val distance: LiveData<Double> = _distance

    private val _text: MutableLiveData<String> = MutableLiveData("")
    val text: LiveData<String> = _text

    private val _startButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val startButtonEvent: LiveData<Unit> = _startButtonEvent

    private val _stopButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val stopButtonEvent: LiveData<Unit> = _stopButtonEvent

    private val _isSuccessSave: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSuccessSave: LiveData<Boolean> = _isSuccessSave

    private var timerTaskforTime: Timer? = null
    private var timerTaskforDistance: Timer? = null
    private var flagForWalk = true

    fun onClickStartButton() {
        _startButtonEvent.value = Unit

    }

    fun onClickStopButton() {
        _stopButtonEvent.value = Unit
    }

    fun saveRecord(polyline: String) {

        if(text.value == null) {

        }else {
            viewModelScope.launch {
                postRecord(
                    RecordForSave(
                        duration = totalDuration.value ?: 0,
                        distance = distance.value ?: 0.0,
                        detail = text.value,
                        polyline
                    )
                )
                    .onStart {  }
                    .onCompletion {  }
                    .collect() { result ->
                        if(result == "SUCCESS") {
                            _isSuccessSave.value = true
                        }
                    }
            }
        }


    }

    fun startRun() {
        if(flagForWalk) {
            flagForWalk = false
            timerTaskforTime = timer(period = 1000) {
               _totalDuration.postValue(_totalDuration.value?.plus(1))
            }
        }
    }

    fun stopRun() {
        if(!flagForWalk){
            flagForWalk = true
            timerTaskforTime?.cancel()
            timerTaskforDistance?.cancel()
        }
    }

    fun setText(text: String) {
        _text.value = text
    }

    fun addDistance(distance: Double) {
        _distance.postValue(_distance.value?.plus(distance))
    }

}
