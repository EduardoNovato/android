package co.cimarrones.bodega.di

import co.cimarrones.bodega.main.modules.details.IModuleRepository
import co.cimarrones.bodega.main.modules.details.ModuleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleRepositoryDI {

    @Binds
    abstract fun bindModuleRepository(impl: ModuleRepository) : IModuleRepository
}