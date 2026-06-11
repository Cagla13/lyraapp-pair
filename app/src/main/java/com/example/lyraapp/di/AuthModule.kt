package com.example.lyraapp.di

import com.example.lyraapp.data.AuthRepository
import com.example.lyraapp.data.FakeAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * [AuthRepository] arayüzünü somut implementasyonuna ([FakeAuthRepository]) bağlar.
 *
 * `@Binds` ile yapıldığından Hilt fazladan kod üretmez; gerçek API implementasyonu
 * eklendiğinde yalnızca buradaki bağlama hedefi değiştirilir.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: FakeAuthRepository): AuthRepository
}