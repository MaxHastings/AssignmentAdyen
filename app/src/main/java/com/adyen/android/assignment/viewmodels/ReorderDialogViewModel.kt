package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogIntent
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for the Reorder Dialog.
 *
 * This ViewModel is responsible for managing the state of the Reorder Dialog and
 * communicating with the SharedSortByViewModel to apply the selected sorting order.
 *
 * @param sharedSortByViewModel Shared ViewModel for managing the sorting order.
 */
@HiltViewModel
class ReorderDialogViewModel @Inject constructor(
    private val sharedSortByViewModel: SharedSortByViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReorderDialogUiState>(ReorderDialogUiState.None)
    val uiState: StateFlow<ReorderDialogUiState> = _uiState.asStateFlow()

    /**
     * Processes user intents and updates the UI state or applies the sorting order.
     *
     * @param intent The user intent to process.
     */
    fun processIntent(intent: ReorderDialogIntent) {
        when (intent) {
            is ReorderDialogIntent.SortByDate -> _uiState.value =
                ReorderDialogUiState.SortByDate

            is ReorderDialogIntent.SortByTitle -> _uiState.value =
                ReorderDialogUiState.SortByTitle

            ReorderDialogIntent.Apply -> {
                when (uiState.value) {
                    ReorderDialogUiState.SortByDate -> sharedSortByViewModel.emitSortEvent(SortBy.DATE)
                    ReorderDialogUiState.SortByTitle -> sharedSortByViewModel.emitSortEvent(SortBy.TITLE)
                    ReorderDialogUiState.None -> {}
                }
            }
        }
    }
}