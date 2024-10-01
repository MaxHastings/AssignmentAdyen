package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.repositories.SortBy
import com.adyen.android.assignment.ui.ReorderDialogIntent
import com.adyen.android.assignment.ui.ReorderDialogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReorderDialogViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<ReorderDialogUiState>(ReorderDialogUiState.None)
    val uiState: StateFlow<ReorderDialogUiState> = _uiState.asStateFlow()

    // SharedFlow to emit sort events
    private val _sortEvents = MutableSharedFlow<SortBy>()
    val sortEvents: SharedFlow<SortBy> = _sortEvents

    fun processIntent(intent: ReorderDialogIntent) {
        when (intent) {
            is ReorderDialogIntent.SortByDate -> _uiState.value = ReorderDialogUiState.SortByDate
            is ReorderDialogIntent.SortByTitle -> _uiState.value = ReorderDialogUiState.SortByTitle
            ReorderDialogIntent.Apply -> {
                when (uiState.value) {
                    ReorderDialogUiState.SortByDate -> emitSortEvent(SortBy.DATE)
                    ReorderDialogUiState.SortByTitle -> emitSortEvent(SortBy.TITLE)
                    ReorderDialogUiState.None -> {}
                }
            }
        }
    }

    // Function to emit sort events to SharedFlow
    private fun emitSortEvent(sortBy: SortBy) {
        viewModelScope.launch {
            _sortEvents.emit(sortBy)
        }
    }
}