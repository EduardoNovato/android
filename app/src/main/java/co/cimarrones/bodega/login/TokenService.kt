package co.cimarrones.bodega.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Base64
import co.cimarrones.bodega.R
import co.cimarrones.bodega.login.login.LoginUIActivity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.nio.charset.Charset


class AuthService {
    companion object {
        var token: String = ""
        var tokenAsObject: TokenAsObject = TokenAsObject("", 0, 0, "", "")
    }
}

@Serializable
data class TokenAsObject(
    val token_type: String,
    val exp: Long,
    val iat: Long,
    val jti: String,
    val user_id: String,
    //val username: String
)

class TokenService {
    companion object {
        private fun sharedPreferences(context: Context): SharedPreferences {
            val fileName = context.resources.getString(R.string.app_name)
            return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }

        fun setToken(context: Context, token: String) {
            val file = sharedPreferences(context)
            with(file.edit()) {
                putString(context.resources.getString(R.string.app_token_key), token)
                apply()
            }

            AuthService.token = token

            if (token.isNotEmpty()) {
                AuthService.tokenAsObject = decodeTokenInfo(token)
            } else {
                AuthService.tokenAsObject = TokenAsObject("", 0, 0, "", "")
            }
        }

        fun getToken(context: Context): String {
            val file = sharedPreferences(context)
            return file.getString(context.resources.getString(R.string.app_token_key), "")
                .toString()
        }

        @OptIn(ExperimentalSerializationApi::class)
        private fun decodeTokenInfo(token: String): TokenAsObject {
            val tokenParts = token.split(".")
            val payload = String(Base64.decode(tokenParts[1], 0), Charset.defaultCharset())
            val json = Json { ignoreUnknownKeys = true }
            return json.decodeFromString(payload)
        }

        fun isTokenValid(token: String): Boolean {
            if (token.isEmpty()) return false
            val payload = decodeTokenInfo(token)
            return payload.exp > System.currentTimeMillis() / 1000
        }

        fun redirectToLogin(context: Context) {
            setToken(context, "")
            val intent = Intent(context, LoginUIActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}