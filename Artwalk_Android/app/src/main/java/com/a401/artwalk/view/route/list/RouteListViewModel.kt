package com.a401.artwalk.view.route.list

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
    }

    private fun getRoutes() {
        viewModelScope.launch {
            // TODO: collect 어떻게 쓰는지 몰라서 collectLatest사용 !! 공부 필요 !!
            fetchRouteList().collectLatest {
                routeList.value = it
            }
        }
    }
}