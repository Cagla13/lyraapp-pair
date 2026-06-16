package com.example.lyraapp.ui.library

import androidx.compose.ui.graphics.vector.ImageVector

sealed interface LibraryContract {

    data class State(
        val isLoading: Boolean = false,
        val selectedFilter: FilterType = FilterType.PLAYLISTS,
        val libraryItems: List<LibraryItem> = emptyList(),
        val userName: String = "",
        val userAvatarText: String = ""
    ) : LibraryContract

    sealed interface Intent : LibraryContract {
        data class SelectFilter(val filterType: FilterType) : Intent
        data object OnSearchClick : Intent
        data object OnCreatePlaylistClick : Intent
    }

    sealed interface SideEffect : LibraryContract {
        data class ShowToast(val message: String) : SideEffect
        data object NavigateToSearch : SideEffect
        data object NavigateToCreatePlaylist : SideEffect
    }

    enum class FilterType(val title: String) {
        PLAYLISTS("Çalma listeleri"),
        ARTISTS("Sanatçılar"),
        ALBUMS("Albümler")
    }

    data class LibraryItem(
        val id: String,
        val title: String,
        val subtitle: String,
        val imageUrl: String? = null,
        val icon: ImageVector? = null,
        val isPinned: Boolean = false
    )
}