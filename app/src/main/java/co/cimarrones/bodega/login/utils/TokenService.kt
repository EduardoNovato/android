package co.cimarrones.bodega.login.utils


import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.SharedPreferences
import android.util.Base64
import co.cimarrones.bodega.R
import co.cimarrones.bodega.httpService.JsonParserConfig
import co.cimarrones.bodega.login.bodega_login.LoginUIViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import java.nio.charset.Charset


class AuthService {
    companion object {
        var token: String = ""
        var tokenAsObject: TokenAsObject = TokenAsObject("", "", "", "", "")
    }
}

class TokenService {

    companion object {

        private fun getSharedPreferencesFile(context: Context): SharedPreferences {
            val fileName = context.resources.getString(R.string.app_name)
            return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }

        fun setToken(context: Context, token: String) {

            val file = getSharedPreferencesFile(context)
            with(file.edit()) {
                putString("token", token)
                apply()
            }

            AuthService.token = token

            if (token.isNotEmpty()) {
                AuthService.tokenAsObject = decodeTokenInfo(token)
            } else {
                AuthService.tokenAsObject = TokenAsObject("", "", "", "", "")
            }
        }

        fun getToken(context: Context): String {
            val file = getSharedPreferencesFile(context)
            return file.getString("token", "").toString()
        }


        fun redirectToLogin(context: Context) {
            setToken(context, "")
            val intent = Intent(context, LoginUIViewModel::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        @OptIn(ExperimentalSerializationApi::class)
        private fun decodeTokenInfo(token: String): TokenAsObject {
            val tokenParts = token.split(".")
            val payload = String(Base64.decode(tokenParts[1], 0), Charset.defaultCharset())
            return JsonParserConfig.decodeFromString(payload)
        }

        fun isTokenValid(token: String): Boolean {
            if (token.isEmpty()) return false
            val payload = decodeTokenInfo(token)
            if (payload.exp.isEmpty()) return false
            return payload.exp.toLong() > System.currentTimeMillis() / 1000
        }
    }
}

@Serializable
data class TokenAsObject(
    val company_id: String,
    val user_id: String,
    val email: String,
    val exp: String,
    val iss: String
)


