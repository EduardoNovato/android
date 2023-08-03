package co.cimarrones.bodega.login.bodega_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.cimarrones.bodega.R
import co.cimarrones.bodega.httpService.RetrofitBuilder
import co.cimarrones.bodega.login.AuthRepository
import co.cimarrones.bodega.ui.theme.BodegaTheme
import co.cimarrones.bodega.ui.theme.Shapes
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFormUI(vm: LoginUIViewModel = hiltViewModel()) {
    val isDarkTheme = isSystemInDarkTheme()
    val uiState by vm.uiState.collectAsState()

    val focusRequester = remember { FocusRequester() }

    fun composeModifier(testTag: String): Modifier {
        val padding = 8.dp
        return Modifier
            .testTag(testTag)
            .padding(padding)
            .fillMaxWidth()
    }
    Box(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                focusRequester.requestFocus()
            }
            .focusRequester(focusRequester)
            .focusable()

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 32.dp, vertical = 60.dp)
        ) {
            if (!isDarkTheme) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_login),
                    contentDescription = "Here goes the company logo, trade mark, etc",
                    modifier = Modifier.testTag("CompanyImage")
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_login_d),
                    contentDescription = "Here goes the company logo, trade mark, etc",
                    modifier = Modifier.testTag("CompanyImage")
                )
            }

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { vm.onDniChange(it) },
                modifier = composeModifier("dni")
                    .onFocusChanged { vm.onDniFocusChange(it) },
                enabled = !uiState.loading,
                label = { Text(stringResource(id = R.string.email_label)) },
                isError = uiState.emailError,
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { vm.onPasswordChange(it) },
                modifier = composeModifier("password")
                    .onFocusChanged { vm.onPasswordChange(it) },
                enabled = !uiState.loading,
                label = { Text(text = stringResource(id = R.string.password_label)) },
                isError = uiState.passwordError,
                visualTransformation = PasswordVisualTransformation(),
            )

            Button(
                onClick = { vm.postLogin() },
                modifier = composeModifier("submit-button")
                    .height(50.dp),
                enabled = vm.isFormValid() && !uiState.loading,
                shape = Shapes.large,
                content = { Text(text = stringResource(id = R.string.login_label_button)) }
            )
        }
        if (uiState.emailError || uiState.passwordError) {
            Snackbar(composeModifier("SnackBarErrors")) {
                val textToShow =
                    if (uiState.emailError) R.string.message_invalid_email
                    else R.string.message_invalid_password
                Text(text = stringResource(textToShow))
            }
        }

        if (uiState.loginErrorMsgStringId != 0) {
            Snackbar(composeModifier("SnackBarErrors")) {
                Text(text = stringResource(uiState.loginErrorMsgStringId))
            }
        }

        if (uiState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .testTag("loading-animation")
                    .height(75.dp)
                    .width(75.dp)
                    .align(Alignment.Center),
                strokeWidth = 6.dp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PrevLoginFormUI() {
    val resApi = RetrofitBuilder.getApiService(LocalContext.current)
    BodegaTheme {
        LoginFormUI(LoginUIViewModel(AuthRepository(Dispatchers.IO, resApi)))
    }
}