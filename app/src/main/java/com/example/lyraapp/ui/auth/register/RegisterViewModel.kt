package com.example.lyraapp.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyraapp.data.AuthRepository
<<<<<<< HEAD
import com.example.lyraapp.ui.auth.UserStorage
=======
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
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
 * Register ekranının MVI ViewModel'i.
 *
 * Tek giriş noktası [onIntent]'tir. Durum [uiState] üzerinden gözlemlenir; tek seferlik
 * olaylar [effect] kanalından akar. Navigasyon kararları Effect'e dönüştürülür; ViewModel
 * içinde hiçbir Android/Compose/navigasyon bağımlılığı bulunmaz.
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

<<<<<<< HEAD
    private val _uiState = MutableStateFlow(RegisterUiState())
=======
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState())
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _effect = Channel<RegisterEffect>(Channel.BUFFERED)
    val effect: Flow<RegisterEffect> = _effect.receiveAsFlow()

    fun onIntent(intent: RegisterIntent) {
        when (intent) {
<<<<<<< HEAD
            is RegisterIntent.FirstNameChanged -> {
                // Klavyenin kilitlenmemesi ve Türkçe karakterlerin rahat yazılması için
                // girdiyi filtrelemeden direkt state'e alıyoruz. Validasyon aşağıda regex ile yapılacak.
                updateForm { it.copy(firstName = intent.value) }
            }
            is RegisterIntent.LastNameChanged -> {
                // Klavyenin kilitlenmemesi ve Türkçe karakterlerin rahat yazılması için direkt alıyoruz
                updateForm { it.copy(lastName = intent.value) }
            }
            is RegisterIntent.EmailChanged -> updateForm { it.copy(email = intent.value) }
            is RegisterIntent.PhoneNumberChanged -> {
                // Telefon numarası sadece rakamlardan oluşmalı (En fazla 10 hane: 5XXXXXXXXX)
                val filteredPhone = intent.value.filter { it.isDigit() }.take(10)
                updateForm { it.copy(phoneNumber = filteredPhone) }
            }
=======
            is RegisterIntent.FirstNameChanged -> updateForm { it.copy(firstName = intent.value) }
            is RegisterIntent.LastNameChanged -> updateForm { it.copy(lastName = intent.value) }
            is RegisterIntent.PhoneNumberChanged -> updateForm { it.copy(phoneNumber = intent.value) }
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
            is RegisterIntent.PasswordChanged -> updateForm { it.copy(password = intent.value) }
            is RegisterIntent.TermsAcceptedChanged -> updateForm { it.copy(isTermsAccepted = intent.value) }
            is RegisterIntent.TogglePasswordVisibility -> _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is RegisterIntent.Submit -> submit()
            is RegisterIntent.BackClicked -> sendEffect(RegisterEffect.NavigateBack)
            is RegisterIntent.LoginClicked -> sendEffect(RegisterEffect.NavigateToLogin)
        }
    }

    /** Form alanını günceller; güç göstergesi ve buton aktifliğini yeniden türetir. */
    private fun updateForm(transform: (RegisterUiState) -> RegisterUiState) {
        _uiState.update { current ->
            val updated = transform(current)
            updated.copy(
                passwordStrength = updated.password.passwordStrength(),
                isRegisterEnabled = updated.isFormValid(),
            )
        }
    }

    private fun submit() {
        val state = _uiState.value
        if (!state.isRegisterEnabled || state.isLoading) return

<<<<<<< HEAD
        // İSTEK: Aynı mail adresiyle tekrar kayıt olmaya çalışılırsa butonu kapatmak yerine
        // tıklamaya izin verip sonradan hata mesajını alt taraftaki Snackbar üzerinden gösteriyoruz.
        val existingUser = UserStorage.registeredUser
        if (existingUser != null && existingUser.email.equals(state.email, ignoreCase = true)) {
            sendEffect(RegisterEffect.ShowError("Bu e-posta adresi ile hesap bulunmakta!"))
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Uzak sunucu kayıt çağrısı
            val result = authRepository.register(
                firstName = state.firstName,
                lastName = state.lastName,
                email = state.email,
                phoneNumber = state.phoneNumber,
                password = state.password,
            )

            _uiState.update { it.copy(isLoading = false) }

            result
                .onSuccess {
                    // Kayıt başarılı olduğunda kullanıcı verilerini yerel hafızaya yazıyoruz
                    UserStorage.registeredUser = UserStorage.UserData(
                        firstName = state.firstName,
                        lastName = state.lastName,
                        email = state.email,
                        phoneNumber = state.phoneNumber,
                        password = state.password
                    )
                    _effect.send(RegisterEffect.NavigateToLogin) // Kayıttan sonra login'e yönlendiriyoruz
                }
=======
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = authRepository.register(
                firstName = state.firstName,
                lastName = state.lastName,
                phoneNumber = state.phoneNumber,
                password = state.password,
            )
            _uiState.update { it.copy(isLoading = false) }

            result
                .onSuccess { _effect.send(RegisterEffect.NavigateToHome) }
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
                .onFailure { error ->
                    _effect.send(RegisterEffect.ShowError(error.message ?: "Kayıt başarısız."))
                }
        }
    }

    private fun sendEffect(effect: RegisterEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}

/** Kayıt butonunun aktif olması için validasyon (ekran kuralı: en az 8 karakter, bir rakam). */
<<<<<<< HEAD
private fun RegisterUiState.isFormValid(): Boolean {
    // Türkçe karakterleri (büyük/küçük), İngilizce harfleri ve boşlukları kabul eden validasyon Regex'i
    val namePattern = Regex("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$")

    val isFirstNameValid = firstName.isNotBlank() && firstName.matches(namePattern)
    val isLastNameValid = lastName.isNotBlank() && lastName.matches(namePattern)


    return isFirstNameValid &&
            isLastNameValid &&
            email.isNotBlank() &&
            phoneNumber.isNotBlank() &&
            password.isPasswordPolicyValid() &&
            isTermsAccepted
}
=======
private fun RegisterUiState.isFormValid(): Boolean =
    firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            phoneNumber.isNotBlank() &&
            password.isPasswordPolicyValid() &&
            isTermsAccepted
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede

/** Şifre politikası: en az 8 karakter ve en az bir rakam. */
private fun String.isPasswordPolicyValid(): Boolean =
    length >= MIN_PASSWORD_LENGTH && any(Char::isDigit)

/**
<<<<<<< HEAD
 * Şifre gücünü 0..[RegisterUiState.PASSWORD_STRENGTH_MAX] aralığında türetir.
=======
 * Şifre gücünü 0..[RegisterUiState.PASSWORD_STRENGTH_MAX] aralığında türetir:
 * uzunluk, rakam ve harf ölçütlerinden her biri bir segment doldurur.
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
 */
private fun String.passwordStrength(): Int {
    if (isEmpty()) return 0
    var score = 0
    if (length >= MIN_PASSWORD_LENGTH) score++
    if (any(Char::isDigit)) score++
    if (any(Char::isLetter)) score++
    return score.coerceAtMost(RegisterUiState.PASSWORD_STRENGTH_MAX)
}

private const val MIN_PASSWORD_LENGTH = 8