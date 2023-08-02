package co.cimarrones.bodega.login.bodega_login

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.cimarrones.bodega.R
import co.cimarrones.bodega.login.IAuthRepository
import co.cimarrones.bodega.login.utils.isDniValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginUIViewModel  @Inject
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

    fun setLoading(loading: Boolean) {
        _uiState.update { currentState -> currentState.copy(loading = loading) }
    }

    fun onDniChange(dni: String) {
        _uiState.update { currentState -> currentState.copy(dni = dni.trim()) }
    }

    fun onPasswordChange(pass: String) {
        _uiState.update { currentState -> currentState.copy(password = pass.trim()) }
    }

    fun onDniFocusChange(fs: FocusState) {
        val currentDni = _uiState.value.dni
        val dniError = !fs.isFocused && currentDni.isNotBlank() && !isDniValid(currentDni)

        _uiState.update { currentState ->
            currentState.copy(dniError = dniError)
        }
    }

    fun onPasswordChange(fs: FocusState) {
        val currentPassword = _uiState.value.password
        val passwordError = !fs.isFocused && currentPassword.isNotBlank()

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

}