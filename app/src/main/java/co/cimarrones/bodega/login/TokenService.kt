package co.cimarrones.bodega.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Base64
import co.cimarrones.bodega.R
import co.cimarrones.bodega.httpService.JsonParserConfig
import co.cimarrones.bodega.login.login.LoginUIActivity
import co.cimarrones.bodega.login.login.LoginUIViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import java.nio.charset.Charset

class TokenService(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        val fileName = context.resources.getString(R.string.app_name)
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun setToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(context.resources.getString(R.string.app_token_key), token)
            apply()
        }
    }

    fun getToken(): String {
        return sharedPreferences.getString(context.resources.getString(R.string.app_token_key), "")
            ?: ""
    }

    fun redirectToLogin() {
        setToken("")
        val intent = Intent(context, LoginUIActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}