package com.a401.artwalk.view.route.draw

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.Marker
import com.a401.domain.model.Route
import com.a401.domain.usecase.GetRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RouteDrawViewModel @Inject constructor(
    private val getRouteUseCase: GetRouteUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _durationHour: MutableLiveData<Int> = MutableLiveData(1)
    val durationHour: LiveData<Int> = _durationHour

    private val _durationMinute: MutableLiveData<Int> = MutableLiveData(2)
    val durationMinute: LiveData<Int> = _durationMinute

    private val _durationSecond: MutableLiveData<Int> = MutableLiveData(3)
    val durationSecond: LiveData<Int> = _durationSecond

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

        when(_markerStack.size) {
            0 -> {

            }
            1 -> {
                val route = getRouteUseCase(_markerStack.peek(), newMarker)
                _routeStack.push(
                    route
                )
                _lastRoute.value = route
            }
            else -> {
                val route = getRouteUseCase(_markerStack.peek(), newMarker)
                _routeStack.push(
                    route
                )
                _lastRoute.value = route
                postDeleteLastMarkerEvent()

            }
        }
        _markerStack.push(newMarker)

    }
}