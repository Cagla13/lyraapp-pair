package com.example.lyraapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyraapp.data.AuthRepository
import com.example.lyraapp.ui.auth.UserStorage
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

/**
 * Login ekranının MVI ViewModel'i.
 *
 * Tek giriş noktası [onIntent]'tir. Durum [uiState] üzerinden gözlemlenir; tek seferlik
 * olaylar [effect] kanalından akar.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect: Flow<LoginEffect> = _effect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> updateForm { it.copy(email = intent.value) }
            is LoginIntent.PhoneNumberChanged -> {
                val filteredPhone = intent.value.filter { it.isDigit() }.take(10)
                updateForm { it.copy(phoneNumber = filteredPhone) }
            }
            is LoginIntent.PasswordChanged -> updateForm { it.copy(password = intent.value) }
            is LoginIntent.TogglePasswordVisibility -> _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is LoginIntent.Submit -> submit()
            is LoginIntent.RegisterClicked -> viewModelScope.launch { _effect.send(LoginEffect.NavigateToRegister) }
        }
    }

    /** Form alanını günceller ve giriş butonunun aktifliğini yeniden türetir. */
    private fun updateForm(transform: (LoginUiState) -> LoginUiState) {
        _uiState.update { current ->
            val updated = transform(current)
            updated.copy(isLoginEnabled = updated.isFormValid())
        }
    }

    private fun submit() {
        val state = _uiState.value
        if (!state.isLoginEnabled || state.isLoading) return

        // Sistemde kayıtlı bir kullanıcı var mı kontrolü
        val registeredUser = UserStorage.registeredUser
        if (registeredUser == null) {
            viewModelScope.launch {
                _effect.send(LoginEffect.ShowError("Sistemde kayıtlı kullanıcı bulunamadı! Lütfen önce kayıt olun."))
            }
            return
        }

        // Bilgilerin kayıtlı kullanıcı verileriyle eşleşme kontrolü
        val isEmailMatch = registeredUser.email.equals(state.email, ignoreCase = true)
        val isPhoneMatch = registeredUser.phoneNumber == state.phoneNumber
        val isPasswordMatch = registeredUser.password == state.password

        if (!isEmailMatch || !isPhoneMatch || !isPasswordMatch) {
            viewModelScope.launch {
                _effect.send(LoginEffect.ShowError("Giriş bilgileri hatalı. Lütfen tekrar deneyin."))
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = authRepository.login(state.phoneNumber, state.password)
            _uiState.update { it.copy(isLoading = false) }

            result
                .onSuccess { _effect.send(LoginEffect.NavigateToHome) }
                .onFailure { error ->
                    _effect.send(LoginEffect.ShowError(error.message ?: "Giriş başarısız."))
                }
        }
    }
}

/** Giriş butonunun aktif olması için form validasyonu. */
private fun LoginUiState.isFormValid(): Boolean =
    email.isNotBlank() && phoneNumber.length == 10 && password.isNotBlank()