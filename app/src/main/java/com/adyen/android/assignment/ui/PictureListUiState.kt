package com.adyen.android.assignment.ui

import com.adyen.android.assignment.api.model.AstronomyPicture

sealed class PictureListUiState {
    data object Loading : PictureListUiState()
    data class Success(val pictures: List<AstronomyPicture>) : PictureListUiState()
    data class NetworkError(val message: String) : PictureListUiState()
    data class Error(val message: String) : PictureListUiState()
}