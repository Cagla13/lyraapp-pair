package com.example.lyraapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyraapp.ui.auth.UserStorage
import com.example.lyraapp.ui.favorites.FavoritesStorage
import com.example.lyraapp.ui.favorites.SongUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect: Flow<HomeEffect> = _effect.receiveAsFlow()

    init {
        loadMockContent()
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.QuickPickClicked -> {
                _uiState.update { current ->
                    val selected = current.quickPicks.find { it.id == intent.itemId }
                    val isFav = selected?.let { item ->
                        FavoritesStorage.savedSongsList.any { it.id == item.id }
                    } ?: false
                    current.copy(currentPlayingTrack = selected, isPlaying = true, isFavorite = isFav)
                }
            }
            is HomeIntent.TrackClicked -> {
                // Navigasyon efekti tetikleniyor
                viewModelScope.launch {
                    _effect.send(HomeEffect.NavigateToDetails(intent.itemId))
                }

                // Oynatılan şarkı durum güncellemesi
                _uiState.update { current ->
                    val selected = current.recentlyPlayed.find { it.id == intent.itemId }
                        ?: current.customPlaylists.find { it.id == intent.itemId }
                    val isFav = selected?.let { item ->
                        FavoritesStorage.savedSongsList.any { it.id == item.id }
                    } ?: false
                    current.copy(currentPlayingTrack = selected, isPlaying = true, isFavorite = isFav)
                }
            }
            HomeIntent.TogglePlayPause -> _uiState.update { it.copy(isPlaying = !it.isPlaying) }
            HomeIntent.ToggleFavorite -> {
                val currentTrack = _uiState.value.currentPlayingTrack
                if (currentTrack != null) {
                    val trackId = currentTrack.id
                    val isCurrentlyFav = FavoritesStorage.savedSongsList.any { it.id == trackId }

                    if (isCurrentlyFav) {
                        FavoritesStorage.savedSongsList.removeAll { it.id == trackId }
                    } else {
                        val trackDuration = when (trackId) {
                            "1" -> "3:34"
                            "2" -> "4:07"
                            "3" -> "3:43"
                            "4" -> "3:25"
                            "5" -> "4:29"
                            "6" -> "3:15"
                            "7" -> "4:02"
                            "8" -> "3:50"
                            "9" -> "2:58"
                            else -> "3:30"
                        }

                        val newFavorite = SongUiModel(
                            id = currentTrack.id,
                            title = currentTrack.title,
                            artist = currentTrack.subtitle ?: "Bilinmeyen Sanatçı",
                            duration = trackDuration,
                            isPlaying = false
                        )
                        FavoritesStorage.savedSongsList.add(newFavorite)
                    }
                    _uiState.update { it.copy(isFavorite = !isCurrentlyFav) }
                }
            }
            HomeIntent.ProfileClicked -> viewModelScope.launch {
                _effect.send(HomeEffect.NavigateToProfile)
            }
            HomeIntent.SeeAllRecentlyPlayedClicked -> {
                // İhtiyaca göre doldurulabilir
            }
        }
    }

    fun checkCurrentTrackFavoriteStatus() {
        val currentTrack = _uiState.value.currentPlayingTrack
        if (currentTrack != null) {
            val isFav = FavoritesStorage.savedSongsList.any { it.id == currentTrack.id }
            _uiState.update { it.copy(isFavorite = isFav) }
        }
    }

    private fun loadMockContent() {
        // Kayıtlı kullanıcı kontrolü yapılarak ad soyad ve baş harfler dinamik atanıyor
        val user = UserStorage.registeredUser
        val dynamicName = if (user != null) "${user.firstName} ${user.lastName}" else "Misafir Kullanıcı"

        val firstInitial = user?.firstName?.firstOrNull()?.uppercase() ?: ""
        val lastInitial = user?.lastName?.firstOrNull()?.uppercase() ?: ""
        val dynamicAvatarText = if (user != null) "$firstInitial$lastInitial" else "MK"

        _uiState.update {
            HomeUiState(
                userName = dynamicName,
                userAvatarText = dynamicAvatarText,
                quickPicks = listOf(
                    PlayableItem("1", "Gece Sürüşü", "Sakin Ritmler", gradientIndex = 0),
                    PlayableItem("2", "Sabah Kahvesi", "Akustik", gradientIndex = 1),
                    PlayableItem("3", "Neon Sokaklar", "Şehir Işıkları", gradientIndex = 2),
                    PlayableItem("4", "Odaklan", "Lo-Fi", gradientIndex = 3),
                    PlayableItem("5", "Derin Mavi", "Okyanus", gradientIndex = 4),
                    PlayableItem("6", "Yaz Anıları", "Pop", gradientIndex = 5)
                ),
                recentlyPlayed = listOf(
                    PlayableItem("3", "Neon Sokaklar", "Şehir Işıkları"),
                    PlayableItem("5", "Derin Mavi", "Okyanus"),
                    PlayableItem("7", "Yıldız Tozu", "Polaris")
                ),
                customPlaylists = listOf(
                    PlayableItem("8", "Akustik Yolculuk", "Sakin Ritmler"),
                    PlayableItem("9", "Yeraltı Beats", "Lo-Fi Evreni")
                ),
                currentPlayingTrack = PlayableItem("3", "Neon Sokaklar", "Şehir Işıkları"),
                isFavorite = false,
                isPlaying = false,
                isLoading = false
            )
        }
    }
}