package co.cimarrones.bodega.httpService

import co.cimarrones.bodega.login.TokenService
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenService: TokenService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenService.getToken()
        val requestBuilder = chain.request().newBuilder()
        if (token != "") {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}

class RedirectInterceptor(private val tokenService: TokenService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 401) {
            tokenService.redirectToLogin()
        }
        return response
    }
}

