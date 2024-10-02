package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogIntent
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogUiState
import com.adyen.android.assignment.usecases.SortPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for the Reorder Dialog.
 *
 * This ViewModel is responsible for managing the state of the Reorder Dialog and
 * sorting pictures based on user selection.
 *
 * @param sortPicturesUseCase Use case for sorting pictures by title or date.
 */
@HiltViewModel
class ReorderDialogViewModel @Inject constructor(
    private val sortPicturesUseCase: SortPicturesUseCase
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
                    ReorderDialogUiState.SortByDate ->
                    {
                        sortPicturesUseCase(selector = { it.date }, true)
                    }
                    ReorderDialogUiState.SortByTitle ->
                    {
                        sortPicturesUseCase(selector = { it.title }, true)
                    }
                    ReorderDialogUiState.None -> {}
                }
            }
        }
    }
}