package com.adyen.android.assignment.ui.redorderDialog

/**
 * Sealed class representing the different user intents for the Reorder Dialog.
 */
sealed class ReorderDialogIntent {
    /**
     * Intent to sort pictures by date.
     */
    data object SortByDate : ReorderDialogIntent()
    /**
     * Intent to sort pictures by title.
     */
    data object SortByTitle : ReorderDialogIntent()
    /**
     * Intent to apply the selected sorting order.
     */
    data object Apply : ReorderDialogIntent()
}