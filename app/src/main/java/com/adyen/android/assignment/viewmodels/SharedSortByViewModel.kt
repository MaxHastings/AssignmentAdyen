package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Shared ViewModel for managing the sorting order.
 *
 * This ViewModel exposes a SharedFlow to emit sorting events that can be observed by other ViewModels.
 *
 * @param dispatcher Coroutine dispatcher for running asynchronous operations.
 */
@Singleton
class SharedSortByViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _sortEvents = MutableSharedFlow<SortBy>()
    val sortEvents: SharedFlow<SortBy> = _sortEvents.asSharedFlow()

    /**
     * Emits a sorting event with the given SortBy value.
     *
     * @param sortBy The sorting order to emit.
     */
    fun emitSortEvent(sortBy: SortBy) {
        viewModelScope.launch(dispatcher) {
            _sortEvents.emit(sortBy)
        }
    }
}

/**
 * Enum class representing the available sorting options.
 */
enum class SortBy {
    DATE, TITLE
}