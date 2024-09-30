package com.adyen.android.assignment.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adyen.android.assignment.repositories.PlanetaryRepository
import com.adyen.android.assignment.repositories.PlanetaryResult
import com.adyen.android.assignment.ui.PictureListUiState
import com.adyen.android.assignment.usecases.GetPicturesUseCase
import com.adyen.android.assignment.usecases.SortPicturesByDateUseCase
import com.adyen.android.assignment.usecases.SortPicturesByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val repository: PlanetaryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PictureListUiState>(PictureListUiState.Loading)
    val uiState: StateFlow<PictureListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                when(val result = getPicturesUseCase()) {
                    is PlanetaryResult.Success -> {
                        _uiState.value = PictureListUiState.Success(pictures = repository.items.value)
                    }
                    is PlanetaryResult.ErrorException ->
                        _uiState.value = PictureListUiState.Error(result.e.message ?: "Unknown error")
                    is PlanetaryResult.ErrorCode ->
                        _uiState.value = PictureListUiState.NetworkError(result.code.toString())
                }
            } catch (e: Exception) {
                _uiState.value = PictureListUiState.Error(e.message ?: "Unknown error")
            }
        }
        viewModelScope.launch {
            repository.items.collect { pictures ->
                _uiState.value = PictureListUiState.Success(pictures = pictures)
            }
        }
    }
}