package co.cimarrones.bodega.di

import co.cimarrones.bodega.login.AuthRepository
import co.cimarrones.bodega.login.IAuthRepository
import co.cimarrones.bodega.login.JWToken
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepository): IAuthRepository
}