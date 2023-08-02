package co.cimarrones.bodega.di

import android.content.Context
import co.cimarrones.bodega.httpService.IRestAPIService
import co.cimarrones.bodega.httpService.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RestApiModule {

    @Provides
    @Singleton
    fun providesRestApi(@ApplicationContext appContext: Context) : IRestAPIService {
        return RetrofitBuilder.getApiService(appContext)
    }
}