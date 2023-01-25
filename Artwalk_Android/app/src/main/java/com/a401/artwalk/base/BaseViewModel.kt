package com.a401.artwalk.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a401.artwalk.di.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel(private val dispatcherProvider: DispatcherProvider) : ViewModel(), DispatcherProvider by dispatcherProvider {

    inline fun onMain(crossinline body: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(main) {
        body(this)
    }

    inline fun onIo(crossinline body: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(io) {
        body(this)
    }

    inline fun onDefault(crossinline body: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(default) {
        body(this)
    }
}