package com.a401.artwalk.view.record.list

import androidx.lifecycle.MutableLiveData
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    private val _totalDuration: MutableLiveData<Int> = MutableLiveData(0)

    private val _distance: MutableLiveData<Double> = MutableLiveData(0.0)

    private val _detail: MutableLiveData<String> = MutableLiveData("")

}