package co.cimarrones.bodega.login

import co.cimarrones.bodega.di.IoDispatcher
import co.cimarrones.bodega.httpService.IRestAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val apiService: IRestAPIService
) : IAuthRepository {
    override suspend fun login(dni: String, password: String): Response<JWToken> {
        return withContext(ioDispatcher) {
            apiService.login(LoginRequest(dni, password))
        }
    }
}

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class JWToken(
    @SerialName("token")
    val token: String
)