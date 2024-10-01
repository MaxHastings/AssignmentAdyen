package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.repositories.PlanetaryResult
import com.adyen.android.assignment.repositories.SortBy
import com.adyen.android.assignment.ui.pictureList.PictureListIntent
import com.adyen.android.assignment.ui.pictureList.PictureListUiState
import com.adyen.android.assignment.usecases.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val sharedSortByViewModel: SharedSortByViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<PictureListUiState>(PictureListUiState.Loading)
    val uiState: StateFlow<PictureListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            sharedSortByViewModel.sortEvents.collect { sortBy ->
                sortPictures(sortBy)
            }
        }
        getPictures()
    }

    private fun getPictures() {
        viewModelScope.launch {
            try {
                when(val result = getPicturesUseCase()) {
                    is PlanetaryResult.Success -> {
                        _uiState.value = PictureListUiState.Success(pictures = result.pictures)
                    }
                    is PlanetaryResult.ErrorCode ->
                        _uiState.value = PictureListUiState.Error(result.code.toString(), false)
                    is PlanetaryResult.ErrorIOException ->
                        _uiState.value = PictureListUiState.Error("No network connection", true)
                    is PlanetaryResult.ErrorException ->
                        _uiState.value = PictureListUiState.Error(result.e.message ?: "Unknown error", false)
                }
            } catch (e: Exception) {
                _uiState.value = PictureListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun processIntent(intent: PictureListIntent) {
        when (intent) {
            is PictureListIntent.Retry -> {
                _uiState.value =
                    PictureListUiState.Loading
                getPictures()
            }
        }
    }

    private fun sortPictures(by: SortBy) {
        when (uiState.value) {
            is PictureListUiState.Success -> {
                val sortedList = when (by) {
                    SortBy.DATE -> (uiState.value as PictureListUiState.Success).pictures.sortedBy { it.date }
                    SortBy.TITLE -> (uiState.value as PictureListUiState.Success).pictures.sortedBy { it.title }
                }
                _uiState.value = PictureListUiState.Success(sortedList)
            }
            is PictureListUiState.Error -> {}
            is PictureListUiState.Loading -> {}
        }
    }
}