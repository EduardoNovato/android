package co.cimarrones.bodega.httpService

import co.cimarrones.bodega.login.JWToken
import co.cimarrones.bodega.login.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IRestAPIService {
    @Headers("Content-Type:application/json")
    @POST("")
    suspend fun login(@Body lr: LoginRequest): Response<JWToken>
}