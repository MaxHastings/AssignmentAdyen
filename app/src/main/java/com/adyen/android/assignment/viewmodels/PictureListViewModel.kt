package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.repositories.PlanetaryResult
import com.adyen.android.assignment.ui.pictureList.PictureListIntent
import com.adyen.android.assignment.ui.pictureList.PictureListUiState
import com.adyen.android.assignment.usecases.GetPicturesUseCase
import com.adyen.android.assignment.usecases.SortPicturesByDateUseCase
import com.adyen.android.assignment.usecases.SortPicturesByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Picture List screen.
 *
 * This ViewModel is responsible for fetching and managing the list of astronomy pictures,
 * handling user intents, and updating the UI state accordingly.
 *
 * @param getPicturesUseCase Use case for fetching the list of pictures.
 * @param sortPicturesByTitleUseCase Use case for sorting pictures by title.
 * @param sortPicturesByDateUseCase Use case for sorting pictures by date.
 * @param sharedSortByViewModel Shared ViewModel for managing the sorting order.
 * @param dispatcher Coroutine dispatcher for running asynchronous operations.
 */
@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val sortPicturesByTitleUseCase: SortPicturesByTitleUseCase,
    private val sortPicturesByDateUseCase: SortPicturesByDateUseCase,
    private val sharedSortByViewModel: SharedSortByViewModel,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<PictureListUiState>(PictureListUiState.Loading)
    val uiState: StateFlow<PictureListUiState> = _uiState.asStateFlow()

    init {
        // Collect sort events from the shared ViewModel and sort pictures accordingly.
        viewModelScope.launch(dispatcher) {
            sharedSortByViewModel.sortEvents.collect { sortBy ->
                sortPictures(sortBy)
            }
        }
    }

    /**
     * Fetches the list of pictures using the getPicturesUseCase.
     * Updates the UI state to Success or Error based on the result.
     */
    fun getPictures() {
        viewModelScope.launch(dispatcher) {
            try {
                when (val result = getPicturesUseCase()) {
                    is PlanetaryResult.Success -> {
                        _uiState.value = PictureListUiState.Success(pictures = result.pictures)
                    }

                    is PlanetaryResult.ErrorCode ->
                        _uiState.value = PictureListUiState.Error(result.code.toString(), false)

                    is PlanetaryResult.ErrorIOException ->
                        _uiState.value = PictureListUiState.Error(result.e.message ?: "Unknown error", true)

                    is PlanetaryResult.ErrorException ->
                        _uiState.value =
                            PictureListUiState.Error(result.e.message ?: "Unknown error", false)
                }
            } catch (e: Exception) {
                _uiState.value = PictureListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    /**
     * Processes user intents and updates the UI state accordingly.
     *
     * @param intent The user intent to process.
     */
    fun processIntent(intent: PictureListIntent) {
        when (intent) {
            is PictureListIntent.GetPictures -> {
                _uiState.value =
                    PictureListUiState.Loading
                getPictures()
            }
        }
    }

    /**
     * Sorts the list of pictures based on the given sorting order.
     *
     * @param by The sorting order (DATE or TITLE).
     */
    private fun sortPictures(by: SortBy) {
        when (uiState.value) {
            is PictureListUiState.Success -> {
                val sortedList = when (by) {
                    SortBy.DATE -> sortPicturesByDateUseCase.invoke(
                        (uiState.value as PictureListUiState.Success).pictures,
                        true
                    )

                    SortBy.TITLE -> sortPicturesByTitleUseCase.invoke(
                        (uiState.value as PictureListUiState.Success).pictures,
                        true
                    )
                }
                _uiState.value = PictureListUiState.Success(sortedList)
            }

            is PictureListUiState.Error -> {}
            is PictureListUiState.Loading -> {}
        }
    }
}