package com.adyen.android.assignment.ui.redorderDialog

sealed class ReorderDialogUiState {
    data object None : ReorderDialogUiState()
    data object SortByTitle : ReorderDialogUiState()
    data object SortByDate : ReorderDialogUiState()
}