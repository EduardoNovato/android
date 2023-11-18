package co.cimarrones.bodega.httpService

import android.content.Context
import co.cimarrones.bodega.login.TokenService
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val ctx = context
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenService.getToken(ctx)
        val requestBuilder = chain.request().newBuilder()
        if (token != "") {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}

class RedirectInterceptor(context: Context) : Interceptor {
    private val ctx = context
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 401) {
            TokenService.redirectToLogin(ctx)
        }
        return response
    }
}

