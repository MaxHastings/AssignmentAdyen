package com.adyen.android.assignment.ui.pictureList

/**
 * Sealed class representing the different user intents for the Picture List screen.
 */
sealed class PictureListIntent {
    /**
     * Intent to fetch the list of pictures.
     */
    data object GetPictures : PictureListIntent()
}