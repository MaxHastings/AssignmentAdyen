package com.adyen.android.assignment.ui.pictureList

sealed class PictureListIntent {
    data object Retry : PictureListIntent()
}