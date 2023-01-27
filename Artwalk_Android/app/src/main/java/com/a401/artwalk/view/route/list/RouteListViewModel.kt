package com.a401.artwalk.view.route.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.RouteForList
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class RouteListViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val routeList = MutableStateFlow<List<RouteForList>>(emptyList())
    var routes: StateFlow<List<RouteListItem>> = routeList.map { routelist ->
        routelist.map { route -> RouteListItem(route)}
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        val temp: ArrayList<RouteForList> = ArrayList()
        temp.add(
            RouteForList(
                "",Date(),"aaa",1
            )
        )
        temp.add(
            RouteForList(
                "",Date(),"bbb",2
            )
        )
        temp.add(
            RouteForList(
                "",Date(),"ccc",3
            )
        )

        routeList.value = temp
    }



}