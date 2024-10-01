package com.adyen.android.assignment.ui.redorderDialog

sealed class ReorderDialogIntent {
    data object SortByDate : ReorderDialogIntent()
    data object SortByTitle : ReorderDialogIntent()
    data object Apply : ReorderDialogIntent()
}