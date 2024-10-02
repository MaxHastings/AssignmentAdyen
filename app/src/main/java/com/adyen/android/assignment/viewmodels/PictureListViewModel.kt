package com.adyen.android.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.repositories.PlanetaryResult
import com.adyen.android.assignment.ui.pictureList.PictureListIntent
import com.adyen.android.assignment.ui.pictureList.PictureListUiState
import com.adyen.android.assignment.usecases.GetPicturesUseCase
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
 * @param dispatcher Coroutine dispatcher for running asynchronous operations.
 */
@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<PictureListUiState>(PictureListUiState.Loading)
    val uiState: StateFlow<PictureListUiState> = _uiState.asStateFlow()

    /**
     * Fetches the list of pictures using the getPicturesUseCase.
     * Updates the UI state to Success or Error based on the result.
     */
    fun getPictures() {
        viewModelScope.launch(dispatcher) {
            try {
                when (val result = getPicturesUseCase(acceptCached = true)) {
                    is PlanetaryResult.Success -> {
                        _uiState.value = PictureListUiState.Success(pictures = result.pictures)
                    }

                    is PlanetaryResult.ErrorCode ->
                        _uiState.value = PictureListUiState.Error(result.code.toString(), false)

                    is PlanetaryResult.ErrorIOException ->
                        _uiState.value =
                            PictureListUiState.Error(result.e.message ?: "Unknown error", true)

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
}