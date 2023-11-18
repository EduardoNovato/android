package co.cimarrones.bodega.httpService

import android.content.Context
import co.cimarrones.bodega.login.TokenService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull

object RetrofitBuilder {

    private const val BASE_URL = "http://192.168.1.104:8080"
    @OptIn(ExperimentalSerializationApi::class)
    fun getApiService(context: Context): IRestAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JsonParserConfig.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .client(okhttpClient(context))
            .build()

        return retrofit.create(IRestAPIService::class.java)
    }
}

private fun okhttpClient(context: Context): OkHttpClient {
    val tokenService = TokenService(context)
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenService))
        .addInterceptor(RedirectInterceptor(tokenService))
        .build()
}
