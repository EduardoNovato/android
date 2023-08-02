package co.cimarrones.bodega.login.bodega_login

data class LoginFormUIState(
    val loading: Boolean = false,
    val dni: String = "",
    val password: String = "",
    val dniError: Boolean = false,
    val passwordError: Boolean = false,
    val internetConnection: Boolean = false,
    val loginErrorMsgStringId: Int = 0,
    val errorMsgStringId: Int = 0,
)