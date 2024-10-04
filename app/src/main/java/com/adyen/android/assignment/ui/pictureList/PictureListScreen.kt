package com.adyen.android.assignment.ui.pictureList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialog
import com.adyen.android.assignment.viewmodels.PictureListViewModel

/**
 * Composable function that displays the Picture List screen.
 *
 * This screen displays a list of astronomy pictures fetched from the ViewModel.
 * It handles different UI states (Loading, Success, Error) and shows a loading indicator,
 * the list of pictures, or an error message accordingly.
 *
 * @param viewModel The PictureListViewModel used to fetch and manage the pictures.
 */
@Composable
fun PictureListScreen(viewModel: PictureListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    // Fetch pictures when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.processIntent(PictureListIntent.GetPictures())
    }

    when (val currentState = uiState) {
        is PictureListUiState.Loading -> LoadingScreen()
        is PictureListUiState.Success -> {
            if (showDialog) {
                ReorderDialog(onDismiss = { applied ->
                    if (applied) {
                        viewModel.processIntent(PictureListIntent.GetPictures())
                    }
                    showDialog = false
                }
                )
            }
            PictureListContent(currentState.pictures,
                onShowDialogChange = { showDialog = it },
                onRefresh = { viewModel.processIntent(PictureListIntent.GetPictures(false)) }
            )
        }

        is PictureListUiState.Error -> ErrorScreen(
            onRetry = { viewModel.processIntent(PictureListIntent.GetPictures(false)) },
            currentState.message,
            currentState.showNetworkSettings
        )
    }
}