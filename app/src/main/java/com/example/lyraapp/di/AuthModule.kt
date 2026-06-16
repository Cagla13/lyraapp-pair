package com.example.lyraapp.di

import com.example.lyraapp.data.AuthRepository
import com.example.lyraapp.data.FakeAuthRepository
<<<<<<< HEAD
import dagger.Binds
import dagger.Module
=======
import dagger.Module
import dagger.Provides
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
<<<<<<< HEAD
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
=======
 * Hilt için bağımlılık modülü.
 * @Provides kullanarak bağımlılıkları açıkça tanımlıyoruz;
 * bu yöntem KSP'nin tip tahminleme hatalarını (STAR null) kesin olarak engeller.
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(impl: FakeAuthRepository): AuthRepository {
        return impl
    }
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
}