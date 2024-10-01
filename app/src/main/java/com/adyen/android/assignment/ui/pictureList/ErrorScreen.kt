package com.adyen.android.assignment.ui.pictureList

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.viewmodels.PictureListViewModel

@Composable
fun ErrorScreen(viewModel: PictureListViewModel, message: String, showNetworkSettings: Boolean = false) {
    val typography = MaterialTheme.typography
    val context = LocalContext.current // To access the context for launching the intent

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Error icon",
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 16.dp),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = "Oops! Something went wrong.",
                style = typography.headlineSmall,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer,
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Retry button
            Button(
                onClick = { viewModel.processIntent(PictureListIntent.GetPictures) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Retry")
            }

            if (showNetworkSettings) {
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                        context.startActivity(intent) // Launches the system network settings screen
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Open Network Settings")
                }
            }
        }
    }
}
