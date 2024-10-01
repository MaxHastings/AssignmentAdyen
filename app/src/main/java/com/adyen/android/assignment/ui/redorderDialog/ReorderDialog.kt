package com.adyen.android.assignment.ui.redorderDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adyen.android.assignment.viewmodels.ReorderDialogViewModel

@Composable
fun ReorderDialog(
    viewModel: ReorderDialogViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {

    val state = viewModel.uiState.collectAsState()

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Sort Pictures") }, // More descriptive title
        text = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) // Add padding
                ) {
                    Text("Title")
                    RadioButton(
                        selected = state.value is ReorderDialogUiState.SortByTitle,
                        onClick = { viewModel.processIntent(ReorderDialogIntent.SortByTitle) }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Date")
                    RadioButton(
                        selected = state.value == ReorderDialogUiState.SortByDate,
                        onClick = { viewModel.processIntent(ReorderDialogIntent.SortByDate) }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.processIntent(ReorderDialogIntent.Apply)
                    onDismiss()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Sort")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary) // Secondary button
            ) {
                Text("Cancel")
            }
        }
    )
}