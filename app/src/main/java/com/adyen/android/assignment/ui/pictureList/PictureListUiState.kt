package com.adyen.android.assignment.ui.pictureList

import com.adyen.android.assignment.api.model.AstronomyPicture

/**
 * Sealed class representing the different UI states of the Picture List screen.
 */
sealed class PictureListUiState {
    /**
     * State indicating that the pictures are being loaded.
     */
    data object Loading : PictureListUiState()
    /**
     * State indicating that the pictures have been successfully loaded.
     *
     * @param pictures The list of astronomy pictures.
     */
    data class Success(val pictures: List<AstronomyPicture>) : PictureListUiState()
    /**
     * State indicating that an error occurred while loading the pictures.
     *
     * @param message The error message to display.
     * @param showNetworkSettings Whether to show a button to open network settings.
     */
    data class Error(val message: String, val showNetworkSettings: Boolean = false) :
        PictureListUiState()
}