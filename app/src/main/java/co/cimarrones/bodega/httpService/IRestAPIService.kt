package co.cimarrones.bodega.httpService

import co.cimarrones.bodega.login.JWToken
import co.cimarrones.bodega.login.LoginRequest
import co.cimarrones.bodega.main.modules.details.container.Container
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface IRestAPIService {
    @Headers("Content-Type:application/json")
    @POST("api/token/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<JWToken>

    @Headers("Content-Type:application/json")
    @GET("containers/")
    suspend fun getContainers(): ResponseBody

    @Headers("Content-Type:application/json")
    @GET("users/{userID}")
    suspend fun getUserById(@Path("userID") userID: String, @QueryMap options: Map<String, String> = emptyMap()): ResponseBody
}