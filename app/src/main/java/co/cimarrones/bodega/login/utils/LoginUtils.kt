package co.cimarrones.bodega.login.utils

import co.cimarrones.bodega.login.bodega_login.LoginUIViewModel
import java.util.regex.Pattern

fun isDniValid(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val pattern = Pattern.compile(emailPattern)
    return pattern.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    return password.length >= LoginUIViewModel.PASSWORD_MIN_LENGTH
}
