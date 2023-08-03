package co.cimarrones.bodega.httpService

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.1.133:8080"

    @OptIn(ExperimentalSerializationApi::class)
    private fun getRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JsonParserConfig.asConverterFactory(MediaType.parse("application/json")!!))
            .client(okhttpClient(context)) // Add our Okhttp client
            .build() //Doesn't require the adapter
    }

    fun getApiService(context: Context): IRestAPIService =
        getRetrofit(context).create(IRestAPIService::class.java)
}

private fun okhttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(context))
        .addInterceptor(RedirectInterceptor(context))
        .build()
}
