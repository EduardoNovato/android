package co.cimarrones.bodega.httpService

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "http://192.168.1.133:8000"
    fun getApiService(context: Context): IRestAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient(context))
            .build()

        return retrofit.create(IRestAPIService::class.java)
    }
}

private fun okhttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(context))
        .addInterceptor(RedirectInterceptor(context))
        .build()
}
