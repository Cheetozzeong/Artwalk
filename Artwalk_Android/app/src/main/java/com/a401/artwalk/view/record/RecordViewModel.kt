package com.a401.artwalk.view.record

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {

    private val _totalDuration: MutableLiveData<Int> = MutableLiveData(0)
    val totalDuration: LiveData<Int> = _totalDuration

    private val _distance: MutableLiveData<Double> = MutableLiveData(0.0)
    val distance: LiveData<Double> = _distance

    private val _startButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val startButtonEvent: LiveData<Unit> = _startButtonEvent

    private val _curButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val curButtonEvent: LiveData<Unit> = _curButtonEvent

    private val _stopButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val stopButtonEvent: LiveData<Unit> = _stopButtonEvent

    private val _pauseButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val pauseButtonEvent: LiveData<Unit> = _pauseButtonEvent

    fun onClickStartButton(){
        _startButtonEvent.value = Unit

    }

    fun onClickCurButton(){
        _curButtonEvent.value = Unit
    }

    fun onClickStopButton(){
        _stopButtonEvent.value = Unit
    }

}
