package com.example.lyraapp.ui.profile

import androidx.compose.runtime.Immutable

object ProfileContract {

    @Immutable
    data class State(
        val userName: String = "",
        val userHandle: String = "",
        val playlistCount: Int = 0,
        val followersCount: Int = 0,
        val followingCount: Int = 0,
        val isDarkTheme: Boolean = false,
        val audioQuality: String = "Yüksek",
        val isOfflineDownloadEnabled: Boolean = true
    )

    sealed interface Intent {
        object LoadUserProfile : Intent
        object ToggleTheme : Intent
        data class OnAudioQualityClick(val currentQuality: String) : Intent
        object OnOfflineDownloadToggle : Intent
        object OnNotificationsClick : Intent
        object OnPrivacyClick : Intent
        object OnHelpSupportClick : Intent
        object OnSettingsClick : Intent
    }

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
        object NavigateToSettings : SideEffect
    }
}