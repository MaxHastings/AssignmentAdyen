package com.adyen.android.assignment.ui.pictureList

import com.adyen.android.assignment.api.model.AstronomyPicture

sealed class PictureListUiState {
    data object Loading : PictureListUiState()
    data class Success(val pictures: List<AstronomyPicture>) : PictureListUiState()
    data class Error(val message: String, val showNetworkSettings: Boolean = false) :
        PictureListUiState()
}