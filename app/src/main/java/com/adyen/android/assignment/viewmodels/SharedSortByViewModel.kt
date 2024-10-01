package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.repositories.SortBy
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedSortByViewModel @Inject constructor() : ViewModel() {

    private val _sortEvents = MutableSharedFlow<SortBy>()
    val sortEvents: SharedFlow<SortBy> = _sortEvents.asSharedFlow()

    fun emitSortEvent(sortBy: SortBy) {
        viewModelScope.launch {
            _sortEvents.emit(sortBy)
        }
    }
}