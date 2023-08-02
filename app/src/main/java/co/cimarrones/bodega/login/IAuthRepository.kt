package co.cimarrones.bodega.login

import retrofit2.Response

interface IAuthRepository {
    suspend fun login(dni: String, password: String): Response<JWToken>
}