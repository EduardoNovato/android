package co.cimarrones.bodega.login.utils

import co.cimarrones.bodega.login.login.LoginUIViewModel
import java.util.regex.Pattern

fun isUserNameValid(email: String): Boolean {
    //val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val userNamePattern = "^[a-zA-Z0-9@/./+/-/_]+$"
    val pattern = Pattern.compile(userNamePattern)
    return pattern.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    return password.length >= LoginUIViewModel.PASSWORD_MIN_LENGTH
}
