package com.example.lyraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.lyraapp.ui.navigation.LyraNavHost
import com.example.lyraapp.ui.theme.LyraAppTheme
import com.example.lyraapp.ui.theme.ThemeManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme by ThemeManager.isDarkTheme.collectAsState()

            LyraAppTheme(darkTheme = isDarkTheme) {
                LyraNavHost(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}