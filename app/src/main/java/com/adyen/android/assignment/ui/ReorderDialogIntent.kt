package com.adyen.android.assignment.ui

sealed class ReorderDialogIntent {
    data object SortByDate : ReorderDialogIntent()
    data object SortByTitle : ReorderDialogIntent()
    data object Apply : ReorderDialogIntent()
}