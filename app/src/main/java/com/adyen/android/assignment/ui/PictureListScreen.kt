package com.adyen.android.assignment.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.ui.composables.PictureItem
import com.adyen.android.assignment.ui.composables.ReorderDialog
import com.adyen.android.assignment.viewmodels.PictureListViewModel

@Composable
fun PictureListScreen(viewModel: PictureListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    when (val currentState = uiState) {
        is PictureListUiState.Loading -> LoadingScreen()
        is PictureListUiState.Success -> {
            if (showDialog) {
                ReorderDialog(onDismiss = { showDialog = false })
            }
            PictureListContent(currentState.pictures, onShowDialogChange = { showDialog = it })
        }
        is PictureListUiState.NetworkError -> ErrorScreen(currentState.message)
        is PictureListUiState.Error -> ErrorScreen(currentState.message)
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun PictureListContent(pictures: List<AstronomyPicture>,  onShowDialogChange: (Boolean) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onShowDialogChange(true) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reorder),
                    contentDescription = "Sort"
                )
            }
        }
    ) { paddingValues -> // paddingValues are for the content to avoid overlapping with the FAB
        LazyColumn(contentPadding = paddingValues) {
            items(pictures) { picture ->
                PictureItem(picture)
            }
        }
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error: $message")
    }
}