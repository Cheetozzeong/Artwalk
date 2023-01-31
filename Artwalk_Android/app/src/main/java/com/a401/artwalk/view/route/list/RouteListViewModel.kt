package com.a401.artwalk.view.route.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.base.BaseViewModel
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import com.a401.domain.model.RouteForList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class RouteListViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val routeList = MutableStateFlow<List<RouteForList>>(emptyList())
    val routes: StateFlow<List<RouteListItem>> = routeList.map { routeForListList ->
        routeForListList.map { routeForList ->
            RouteListItem(routeForList)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        val temp: ArrayList<RouteForList> = ArrayList()
        temp.add(
            RouteForList(
                "",Date(),"aaa","eb{cFqbjfWMFyF{X|CiA",1
            )
        )
        temp.add(
            RouteForList(
                "",Date(),"bbb","",2
            )
        )
        temp.add(
            RouteForList(
                "",Date(),"ccc","",3
            )
        )
        temp.add(
            RouteForList(
                "",Date(),"ddd","",4
            )
        )

        routeList.value = temp
    }



}