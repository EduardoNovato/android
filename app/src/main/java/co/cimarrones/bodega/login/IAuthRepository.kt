package co.cimarrones.bodega.login

import retrofit2.Response

interface IAuthRepository {
    suspend fun login(username: String, password: String): Response<JWToken>
}