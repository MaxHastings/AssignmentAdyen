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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.viewmodels.PictureListViewModel

/**
 * Composable function that displays an error screen.
 *
 * This screen shows an error message and provides options to retry fetching data
 * or open the network settings.
 *
 * @param viewModel The ViewModel used to retry fetching data.
 * @param message The error message to display.
 * @param showNetworkSettings Whether to show a button to open network settings.
 */
@Composable
fun ErrorScreen(
    viewModel: PictureListViewModel,
    message: String,
    showNetworkSettings: Boolean = false
) {
    val typography = MaterialTheme.typography
    val context = LocalContext.current

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
                contentDescription = stringResource(id = R.string.error_icon),
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 16.dp),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = stringResource(id = R.string.error_title),
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

            Button(
                onClick = { viewModel.processIntent(PictureListIntent.GetPictures) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.retry))
            }

            if (showNetworkSettings) {
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.open_network_settings))
                }
            }
        }
    }
}