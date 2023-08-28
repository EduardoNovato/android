package co.cimarrones.bodega.login

import co.cimarrones.bodega.httpService.IRestAPIService
import kotlinx.serialization.Serializable
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: IRestAPIService) :
    IAuthRepository {
    override suspend fun login(username: String, password: String): Response<JWToken> {
        val loginRequest = LoginRequest(username, password)
        return apiService.login(loginRequest)
    }
}


@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class JWToken(
    val token: String
)