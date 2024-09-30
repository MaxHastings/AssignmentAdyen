package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import com.adyen.android.assignment.repositories.PlanetaryRepository
import com.adyen.android.assignment.repositories.SortBy
import com.adyen.android.assignment.ui.ReorderDialogIntent
import com.adyen.android.assignment.ui.ReorderDialogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReorderDialogViewModel @Inject constructor(
    private val repository: PlanetaryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReorderDialogUiState>(ReorderDialogUiState.None)
    val uiState: StateFlow<ReorderDialogUiState> = _uiState.asStateFlow()

    fun processIntent(intent: ReorderDialogIntent) {
        when (intent) {
            is ReorderDialogIntent.SortByDate -> _uiState.value = ReorderDialogUiState.SortByDate
            is ReorderDialogIntent.SortByTitle -> _uiState.value = ReorderDialogUiState.SortByTitle
            ReorderDialogIntent.Apply -> {
                when (uiState.value) {
                    ReorderDialogUiState.SortByDate -> repository.sortPictures(SortBy.DATE)
                    ReorderDialogUiState.SortByTitle -> repository.sortPictures(SortBy.TITLE)
                    ReorderDialogUiState.None -> {}
                }
            }
        }
    }
}