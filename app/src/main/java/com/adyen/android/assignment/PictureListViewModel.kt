package com.adyen.android.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.repositories.PlanetaryResult
import com.adyen.android.assignment.ui.PictureListUiState
import com.adyen.android.assignment.ui.PictureListUserIntent
import com.adyen.android.assignment.usecases.GetPicturesUseCase
import com.adyen.android.assignment.usecases.SortPicturesByDateUseCase
import com.adyen.android.assignment.usecases.SortPicturesByTitleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val sortPicturesByDateUseCase: SortPicturesByDateUseCase,
    private val sortPicturesByTitleUseCase: SortPicturesByTitleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PictureListUiState>(PictureListUiState.Loading)
    val uiState: StateFlow<PictureListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                when(val result = getPicturesUseCase()) {
                    is PlanetaryResult.Success ->
                        _uiState.value = PictureListUiState.Success(pictures = result.pictures)
                    is PlanetaryResult.ErrorException ->
                        _uiState.value = PictureListUiState.Error(result.e.message ?: "Unknown error")
                    is PlanetaryResult.ErrorCode ->
                        _uiState.value = PictureListUiState.NetworkError(result.code.toString())
                }
            } catch (e: Exception) {
                _uiState.value = PictureListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun processIntent(intent: PictureListUserIntent) {
        viewModelScope.launch {
            when (intent) {
                is PictureListUserIntent.SortByDate -> sortPicturesByDate(intent.ascending)
                is PictureListUserIntent.SortByTitle -> sortPicturesByTitle(intent.ascending)
            }
        }
    }

    private fun sortPicturesByDate(ascending: Boolean) {
        when (val currentState = _uiState.value) {
            is PictureListUiState.Success -> {
                val sortedPictures = sortPicturesByDateUseCase(currentState.pictures, ascending)
                _uiState.value = PictureListUiState.Success(pictures = sortedPictures)
            }
            is PictureListUiState.Error -> {} // Do nothing
            PictureListUiState.Loading -> {} // Do nothing
            is PictureListUiState.NetworkError -> {} // Do nothing
        }
    }

    private fun sortPicturesByTitle(ascending: Boolean) {
        when (val currentState = _uiState.value) {
            is PictureListUiState.Success -> {
                val sortedPictures = sortPicturesByTitleUseCase(currentState.pictures, ascending)
                _uiState.value = PictureListUiState.Success(pictures = sortedPictures)
            }
            is PictureListUiState.Error -> {} // Do nothing
            PictureListUiState.Loading -> {} // Do nothing
            is PictureListUiState.NetworkError -> {} // Do nothing
        }
    }
}