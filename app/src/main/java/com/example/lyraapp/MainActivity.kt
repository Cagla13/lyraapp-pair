package com.example.lyraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
<<<<<<< HEAD
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.lyraapp.ui.navigation.LyraNavHost
import com.example.lyraapp.ui.theme.LyraAppTheme
import com.example.lyraapp.ui.theme.ThemeManager
import dagger.hilt.android.AndroidEntryPoint

=======
import com.example.lyraapp.ui.navigation.LyraNavHost
import com.example.lyraapp.ui.theme.LyraAppTheme
import dagger.hilt.android.AndroidEntryPoint
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
<<<<<<< HEAD
            val isDarkTheme by ThemeManager.isDarkTheme.collectAsState()

            LyraAppTheme(darkTheme = isDarkTheme) {
                LyraNavHost(
                    modifier = Modifier.fillMaxSize()
                )
=======
            LyraAppTheme {
                LyraNavHost()
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
            }
        }
    }
}