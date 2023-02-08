package com.a401.artwalk.view.route.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.RouteForList
import com.a401.domain.usecase.FetchRouteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteListViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val fetchRouteList: FetchRouteList
) : BaseViewModel(dispatcherProvider) {

    private val routeList = MutableStateFlow<List<RouteForList>>(emptyList())
    val routes: StateFlow<List<RouteListItem>> = routeList.map { routeForListList ->
        routeForListList.map { routeForList ->
            RouteListItem(routeForList)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        getRoutes()
        Log.d("LifeCycleVM", "init")
    }

    fun getRoutes() {
        viewModelScope.launch {
            fetchRouteList().collectLatest {
                routeList.value = it
            }
        }
    }
}