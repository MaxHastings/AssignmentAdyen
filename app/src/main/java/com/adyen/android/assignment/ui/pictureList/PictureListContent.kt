package com.adyen.android.assignment.ui.pictureList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture

/**
 * Composable function that displays the content of the Picture List screen.
 *
 * This function displays a list of astronomy pictures an action button to open the Reorder Dialog.
 *
 * @param pictures The list of astronomy pictures to display.
 * @param onShowDialogChange Callback function to update the visibility of the Reorder Dialog.
 */
@Composable
fun PictureListContent(
    pictures: List<AstronomyPicture>,
    onShowDialogChange: (Boolean) -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            Row {
                // Refresh Button
                FloatingActionButton(onClick = { onRefresh() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh),
                        contentDescription = "Refresh"
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Sort Button
                FloatingActionButton(onClick = { onShowDialogChange(true) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_reorder),
                        contentDescription = "Sort"
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(pictures) { picture ->
                PictureItem(picture)
            }
        }
    }
}
