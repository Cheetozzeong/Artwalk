package com.a401.artwalk.view.route.draw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.Marker
import com.a401.domain.model.RouteForDraw
import com.a401.domain.usecase.GetRouteForWalkingUseCase
import com.a401.domain.usecase.PostRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RouteDrawViewModel @Inject constructor(
    private val getRouteForWalkingUseCase: GetRouteForWalkingUseCase,
    private val postRoute: PostRouteUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _totalDuration: MutableLiveData<Int> = MutableLiveData(0)
    val totalDuration: LiveData<Int> = _totalDuration

    private val _distance: MutableLiveData<Double> = MutableLiveData(0.0)
    val distance: LiveData<Double> = _distance

    private val _drawButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val drawButtonEvent: LiveData<Unit> = _drawButtonEvent

    private val _markerStack: Stack<Marker> = Stack()

    private val _lastPoint: MutableLiveData<Marker> = MutableLiveData()
    val lastPoint: LiveData<Marker> = _lastPoint

    private val _startPoint: MutableLiveData<Marker> = MutableLiveData()
    val startPoint: LiveData<Marker> = _startPoint

    private val _routeStack: Stack<RouteForDraw> = Stack()

    private val _lastRoute: MutableLiveData<RouteForDraw> = MutableLiveData()
    val lastRoute: LiveData<RouteForDraw> = _lastRoute

    private val _deleteRoute: MutableLiveData<RouteForDraw> = MutableLiveData()
    val deleteRoute: LiveData<RouteForDraw> = _deleteRoute

    private val _isSuccessSave: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSuccessSave: LiveData<Boolean> = _isSuccessSave

    fun onClickDrawButton() {
        // TODO: 그리기 토글 버튼 클릭시 event wrapper로 수정
        _drawButtonEvent.value = Unit
    }

    fun onClickUndoButton() {
        when(_markerStack.size) {
            0 -> {}
            1 -> {
                _markerStack.pop()
                _startPoint.value = MarkerForDelete
            }
            else -> {
                _markerStack.pop()
                _lastPoint.value = _markerStack.peek()
            }
        }
        when(_routeStack.size) {
            0 -> {}
            else -> {
                _deleteRoute.value = _routeStack.pop()
                _distance.value = deleteRoute.value?.distance?.let { _distance.value?.minus(it) }
                _totalDuration.value = deleteRoute.value?.duration?.let { _totalDuration.value?.minus(it) }
            }
        }
    }

    fun saveDrawRoute(polyline: String) {

        viewModelScope.launch {
            postRoute(
                RouteForDraw(
                    duration = totalDuration.value ?: 0,
                    distance = distance.value ?: 0.0,
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

    fun addPointEvent(id: Long, latitude: Double, longitude: Double) {
        val newMarker = Marker(id, latitude, longitude)

        viewModelScope.launch {


            when (_markerStack.size) {
                0 -> {
                    _startPoint.value = newMarker
                }
                else -> {
                    val route = getRouteForWalkingUseCase(_markerStack.peek(), newMarker).getOrThrow()
                    _lastPoint.value = newMarker

                    _routeStack.push(route)
                    _lastRoute.value = route

                    _totalDuration.value = _totalDuration.value?.plus(route.duration)
                    _distance.value = _distance.value?.plus(route.distance)
                }
            }
            _markerStack.push(newMarker)
        }
    }
}