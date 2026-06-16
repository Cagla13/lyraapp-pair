package com.example.lyraapp.ui.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyraapp.ui.auth.UserStorage
import com.example.lyraapp.ui.favorites.FavoritesStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LibraryContract.State())
    val state: StateFlow<LibraryContract.State> = _state.asStateFlow()

    private val _effect = Channel<LibraryContract.SideEffect>(Channel.BUFFERED)
    val effect: Flow<LibraryContract.SideEffect> = _effect.receiveAsFlow()

    init {
        loadUserProfile()
        observeFavorites()
    }

    /**
     * Giriş yapan kullanıcının bilgilerini yerel hafızadan (UserStorage)
     * dinamik olarak çekerek state'e bağlar.
     */
    fun loadUserProfile() {
        val user = UserStorage.registeredUser
        if (user != null) {
            val firstInitial = user.firstName.firstOrNull()?.uppercase() ?: ""
            val lastInitial = user.lastName.firstOrNull()?.uppercase() ?: ""

            _state.update { currentState ->
                currentState.copy(
                    userName = "${user.firstName} ${user.lastName}",
                    userAvatarText = "$firstInitial$lastInitial"
                )
            }
        } else {
            _state.update { currentState ->
                currentState.copy(
                    userName = "Misafir Kullanıcı",
                    userAvatarText = "MK"
                )
            }
        }
    }

    /**
     * Kütüphane ekranı her açıldığında veya başlatıldığında
     * FavoritesStorage içerisindeki güncel listeyi reaktif duruma bağlar.
     */
    fun observeFavorites() {
        _state.update { it.copy(isLoading = true) }

        // FavoritesStorage içindeki listenin gerçek adı: savedSongsList
        val favoriteSongsList = FavoritesStorage.savedSongsList

        _state.update { currentState ->
            if (favoriteSongsList.isEmpty()) {
                currentState.copy(
                    isLoading = false,
                    libraryItems = emptyList()
                )
            } else {
                val totalSongs = favoriteSongsList.size
                val likedSongsItem = LibraryContract.LibraryItem(
                    id = "liked_songs_playlist",
                    title = "Beğenilen Şarkılar",
                    subtitle = "Çalma listesi • $totalSongs şarkı",
                    icon = Icons.Default.Favorite,
                    isPinned = true
                )

                currentState.copy(
                    isLoading = false,
                    libraryItems = listOf(likedSongsItem)
                )
            }
        }
    }

    fun onIntent(intent: LibraryContract.Intent) {
        when (intent) {
            is LibraryContract.Intent.SelectFilter -> {
                _state.update { it.copy(selectedFilter = intent.filterType) }
            }
            LibraryContract.Intent.OnSearchClick -> {
                viewModelScope.launch {
                    _effect.send(LibraryContract.SideEffect.NavigateToSearch)
                }
            }
            LibraryContract.Intent.OnCreatePlaylistClick -> {
                viewModelScope.launch {
                    _effect.send(LibraryContract.SideEffect.ShowToast("Çalma listesi oluşturma yakında eklenecek"))
                }
            }
        }
    }
}