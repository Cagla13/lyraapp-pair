package com.example.lyraapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyraapp.ui.auth.UserStorage
import com.example.lyraapp.ui.theme.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ProfileContract.State())
    val state: StateFlow<ProfileContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileContract.SideEffect>()
    val effect: SharedFlow<ProfileContract.SideEffect> = _effect.asSharedFlow()

    init {
        _state.update { it.copy(isDarkTheme = ThemeManager.isDarkTheme.value) }
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = UserStorage.registeredUser

        // Sadece kullanıcı kayıtlıysa state'i güncelle
        if (user != null) {
            _state.update { currentState ->
                currentState.copy(
                    userName = "${user.firstName} ${user.lastName}",
                    userHandle = if (user.email.startsWith("@")) user.email else "@${user.email.split("@")[0]}"
                )
            }
        }
    }

    fun onIntent(intent: ProfileContract.Intent) {
        when (intent) {
            ProfileContract.Intent.LoadUserProfile -> {
                loadUserProfile()
            }
            ProfileContract.Intent.ToggleTheme -> {
                val newThemeState = !_state.value.isDarkTheme
                _state.update { it.copy(isDarkTheme = newThemeState) }
                ThemeManager.setDarkTheme(newThemeState)

                viewModelScope.launch {
                    val themeName = if (newThemeState) "Koyu Tema" else "Açık Tema"
                    _effect.emit(ProfileContract.SideEffect.ShowToast("$themeName aktif edildi"))
                }
            }
            is ProfileContract.Intent.OnAudioQualityClick -> {
                viewModelScope.launch {
                    _effect.emit(ProfileContract.SideEffect.ShowToast("Ses kalitesi ayarlarına tıklandı"))
                }
            }
            ProfileContract.Intent.OnOfflineDownloadToggle -> {
                _state.update { it.copy(isOfflineDownloadEnabled = !it.isOfflineDownloadEnabled) }
            }
            ProfileContract.Intent.OnNotificationsClick -> {
                viewModelScope.launch { _effect.emit(ProfileContract.SideEffect.ShowToast("Bildirim ayarlarına tıklandı")) }
            }
            ProfileContract.Intent.OnPrivacyClick -> {
                viewModelScope.launch { _effect.emit(ProfileContract.SideEffect.ShowToast("Gizlilik ayarlarına tıklandı")) }
            }
            ProfileContract.Intent.OnHelpSupportClick -> {
                viewModelScope.launch { _effect.emit(ProfileContract.SideEffect.ShowToast("Yardım ve destek merkezine tıklandı")) }
            }
            ProfileContract.Intent.OnSettingsClick -> {
                viewModelScope.launch { _effect.emit(ProfileContract.SideEffect.NavigateToSettings) }
            }
        }
    }
}