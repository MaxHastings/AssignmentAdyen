package com.adyen.android.assignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adyen.android.assignment.MyTheme
import com.adyen.android.assignment.ui.pictureList.PictureListScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity of the application.
 *
 * This activity sets up the main screen content and applies the application theme.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is created.
     *
     * Sets the content view to the PictureListScreen composable function and applies the MyTheme theme.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                PictureListScreen()
            }
        }
    }
}