package com.example.lyraapp.data

import kotlinx.coroutines.delay
import javax.inject.Inject

<<<<<<< HEAD
/**
 * [AuthRepository]'nin sahte (stub) implementasyonu.
 *
 * Gerçek bir ağ çağrısı yapmaz; yalnızca MVI akışının uçtan uca çalışmasını sağlamak için
 * bir gecikme ile ağ davranışını taklit eder. Gerçek API geldiğinde bu sınıf bir
 * ağ tabanlı implementasyonla değiştirilir.
 */
=======

>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
class FakeAuthRepository @Inject constructor() : AuthRepository {

    override suspend fun login(phoneNumber: String, password: String): Result<Unit> {
        delay(NETWORK_DELAY_MS)
        return if (password.isNotBlank()) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Şifre boş olamaz."))
        }
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
<<<<<<< HEAD
        email: String,
=======
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
        phoneNumber: String,
        password: String,
    ): Result<Unit> {
        delay(NETWORK_DELAY_MS)
<<<<<<< HEAD

        return if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
=======
        return if (firstName.isNotBlank() && lastName.isNotBlank() && password.isNotBlank()) {
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Hesap bilgileri eksik."))
        }
    }

    private companion object {
        const val NETWORK_DELAY_MS = 1_000L
    }
}