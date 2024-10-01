package com.adyen.android.assignment.ui.redorderDialog

/**
 * Sealed class representing the different UI states of the Reorder Dialog.
 */
sealed class ReorderDialogUiState {
    /**
     * Initial state of the dialog, no sorting option selected.
     */
    data object None : ReorderDialogUiState()
    /**
     * State indicating that the user has selected to sort by title.
     */
    data object SortByTitle : ReorderDialogUiState()
    /**
     * State indicating that the user has selected to sort by date.
     */
    data object SortByDate : ReorderDialogUiState()
}