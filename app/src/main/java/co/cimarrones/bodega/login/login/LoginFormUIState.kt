package co.cimarrones.bodega.login.login

data class LoginFormUIState(
    val loading: Boolean = false,
    val userName: String = "",
    val password: String = "",
    val userNameError: Boolean = false,
    val passwordError: Boolean = false,
    val internetConnection: Boolean = false,
    val loginErrorMsgStringId: Int = 0,
    val errorMsgStringId: Int = 0,
)
