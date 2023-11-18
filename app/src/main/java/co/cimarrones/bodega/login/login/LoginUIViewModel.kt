package co.cimarrones.bodega.login.login

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.cimarrones.bodega.R
import co.cimarrones.bodega.login.IAuthRepository
import co.cimarrones.bodega.login.utils.isPasswordValid
import co.cimarrones.bodega.login.utils.isUserNameValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginUIViewModel @Inject
constructor(private val authRepository: IAuthRepository) : ViewModel() {

    companion object {
        const val PASSWORD_MIN_LENGTH = 6
    }

    private var _token = MutableLiveData("")

    var token: LiveData<String> = _token

    fun setToken(token: String) {
        _token.value = token
    }
    private val _uiState = MutableStateFlow(LoginFormUIState())
    val uiState: StateFlow<LoginFormUIState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.update { currentState -> currentState.copy(loading = loading) }
    }

    fun onUserNameChange(userName: String) {
        _uiState.update { currentState -> currentState.copy(userName = userName.trim()) }
    }

    fun onPasswordChange(pass: String) {
        _uiState.update { currentState -> currentState.copy(password = pass.trim()) }
    }

    fun onUserNameFocusChange(fs: FocusState) {
        val currentUserName = _uiState.value.userName
        val userNameError = !fs.isFocused && currentUserName.isNotBlank() && !isUserNameValid(currentUserName)

        _uiState.update { currentState ->
            currentState.copy(userNameError = userNameError)
        }
    }

    fun onPasswordChange(fs: FocusState) {
        val currentPassword = _uiState.value.password
        val passwordError =
            !fs.isFocused && currentPassword.isNotBlank() && !isPasswordValid(currentPassword)

        _uiState.update { currentState -> currentState.copy(passwordError = passwordError) }
    }

    fun onNetworkConnectionChange(connected: Boolean) {
        var stringID = 0
        if (!connected) stringID = R.string.network_connectivity_lost_error_message
        _uiState.update { currentState ->
            currentState.copy(
                internetConnection = connected,
                errorMsgStringId = stringID
            )
        }
    }

    fun isFormValid(): Boolean {
        return isUserNameValid(_uiState.value.userName) && isPasswordValid(_uiState.value.password)
    }

    fun postLogin() {
        resetLoginErrorMessage()
        setLoading(true)
        viewModelScope.launch {
            try {
                val response = authRepository.login(_uiState.value.userName, _uiState.value.password)
                if (response.isSuccessful) {
                    val token = response.body()?.access!!
                    _token.postValue(token)
                }
                if (!response.isSuccessful) {
                    handleUnsuccessfulResponse(response.code())
                }
            } catch (e: Exception) {
                if (e is java.net.SocketTimeoutException) _uiState.update { currentState ->
                    currentState.copy(loginErrorMsgStringId = R.string.socket_timeout_error_message)
                }

                if (e is java.net.ConnectException) _uiState.update { currentState ->
                    currentState.copy(loginErrorMsgStringId = R.string.unreachable_server_error_message)
                }
            } finally {
                setLoading(false)
            }
        }
    }

    private fun resetLoginErrorMessage() {
        _uiState.update { currentState ->
            currentState.copy(loginErrorMsgStringId = 0)
        }
    }

    private fun handleUnsuccessfulResponse(responseCode: Int) {
        var emStringId = 0
        when (responseCode) {
            400 -> emStringId = R.string.wrong_credentials_error_message
            404 -> emStringId = R.string.wrong_credentials_error_message
            412 -> emStringId = R.string.disabled_user_error_message
            418 -> emStringId = R.string.account_not_confirmed_error_message
            500 -> emStringId = R.string.internal_server_error_message
        }
        _uiState.update { currentState -> currentState.copy(loginErrorMsgStringId = emStringId) }
    }

}