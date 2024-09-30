package com.adyen.android.assignment.ui

sealed class PictureListUserIntent {
    data class SortByDate(val ascending: Boolean) : PictureListUserIntent()
    data class SortByTitle(val ascending: Boolean) : PictureListUserIntent()
}