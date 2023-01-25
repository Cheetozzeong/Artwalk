package com.a401.artwalk.view.route.draw

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.Marker
import com.a401.domain.model.Route
import com.a401.domain.usecase.GetRouteForWalkingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RouteDrawViewModel @Inject constructor(
    private val getRouteForWalkingUseCase: GetRouteForWalkingUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _totalDuration: MutableLiveData<Int> = MutableLiveData(0)
    val totalDuration: LiveData<Int> = _totalDuration

    private val _distance: MutableLiveData<Float> = MutableLiveData(0f)
    val distance: LiveData<Float> = _distance

    private val _drawButtonEvent: MutableLiveData<Unit> = MutableLiveData()
    val drawButtonEvent: LiveData<Unit> = _drawButtonEvent

    private val _markerStack: Stack<Marker> = Stack()

    private val _lastPointId: MutableLiveData<Long> = MutableLiveData()
    val lastPointId: LiveData<Long> = _lastPointId

    private val _routeStack: Stack<Route> = Stack()

    private val _lastRoute: MutableLiveData<Route> = MutableLiveData()
    val lastRoute: LiveData<Route> = _lastRoute

    fun onClickDrawButton() {
        // TODO: 그리기 토글 버튼 클릭시 event
        _drawButtonEvent.value = Unit
    }

    fun onClickUndoButton() {
        postDeleteLastMarkerEvent()
    }

    private fun postDeleteLastMarkerEvent() {
        _lastPointId.value = _markerStack.pop().markerId
    }


    fun addPointEvent(id: Long, latitude: Double, longitude: Double) {
        val newMarker = Marker(id, latitude, longitude)

        viewModelScope.launch {


            when (_markerStack.size) {
                0 -> {

                }
                1 -> {
                    val route = getRouteForWalkingUseCase(_markerStack.peek(), newMarker).getOrThrow()
                    _routeStack.push(
                        route
                    )
                    _lastRoute.value = route
                    _totalDuration.value = _totalDuration.value?.plus(route.duration)
                    _distance.value = _distance.value?.plus(route.distance)
                }
                else -> {
                    val route = getRouteForWalkingUseCase(_markerStack.peek(), newMarker).getOrThrow()
                    _routeStack.push(
                        route
                    )
                    _lastRoute.value = route
                    _totalDuration.value = _totalDuration.value?.plus(route.duration)
                    _distance.value = _distance.value?.plus(route.distance)
                    postDeleteLastMarkerEvent()

                }
            }
            _markerStack.push(newMarker)

        }
    }
}