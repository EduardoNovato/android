package co.cimarrones.bodega.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import co.cimarrones.bodega.R
import co.cimarrones.bodega.login.login.LoginUIViewModel

class TokenService {

        companion object {

            private fun sharedPreferences(context: Context): SharedPreferences {
                val fileName = context.resources.getString(R.string.app_name)
                return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
            }

            private fun setToken(context: Context, token: String) {
                val file = sharedPreferences(context)
                with(file.edit()) {
                    putString(context.resources.getString(R.string.app_token_key), token)
                    apply()
                }
            }

            fun getToken(context: Context): String {
                val file = sharedPreferences(context)
                return file.getString(context.resources.getString(R.string.app_token_key), null) ?: ""
            }

            fun redirectToLogin(context: Context) {
                setToken(context, "")
                val intent = Intent(context, LoginUIViewModel::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }
