package co.cimarrones.bodega.login.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.cimarrones.bodega.R
import co.cimarrones.bodega.login.SING_UP
import co.cimarrones.bodega.login.WELKON
import co.cimarrones.bodega.ui.theme.Shapes

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

    @Composable
    fun Logo() {
        val logoResource = if (isDarkTheme) R.drawable.ic_logo_login_d else R.drawable.ic_logo_login
        Image(
            painter = painterResource(id = logoResource),
            contentDescription = "stringResource(id = R.string.logo_content_description)",
            modifier = Modifier.testTag("icon-logo-login")
        )
    }

    @Composable
    fun EmailField() {
        OutlinedTextField(
            value = uiState.userName,
            onValueChange = { vm.onUserNameChange(it) },
            modifier = composeModifier("email")
                .onFocusChanged { vm.onUserNameFocusChange(it) },
            enabled = !uiState.loading,
            label = { Text(stringResource(id = R.string.email_label)) },
            isError = uiState.userNameError,
        )
    }

    @Composable
    fun PasswordField() {
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { vm.onPasswordChange(it) },
            modifier = composeModifier("password")
                .onFocusChanged { vm.onPasswordChange(it) },
            enabled = !uiState.loading,
            label = { Text(stringResource(id = R.string.password_label)) },
            isError = uiState.passwordError,
            visualTransformation = PasswordVisualTransformation(),
        )
    }

    @Composable
    fun ErrorSnackBar() {
        val errorMsgStringId =
            if (uiState.userNameError) R.string.message_invalid_email
            else if (uiState.passwordError) R.string.message_invalid_password
            else uiState.loginErrorMsgStringId

        if (errorMsgStringId != 0) {
            Snackbar(composeModifier("snack-bar")) {
                Text(text = stringResource(errorMsgStringId))
            }
        }
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

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 32.dp, vertical = 60.dp)
        ) {
            Logo()

            EmailField()

            PasswordField()

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    vm.postLogin()
                },
                modifier = composeModifier("submit-button")
                    .height(50.dp),
                enabled = vm.isFormValid() && !uiState.loading,
                shape = Shapes.medium,
                content = { Text(text = stringResource(id = R.string.login_label_button)) }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = buildAnnotatedString {
                        append("Already have an account?")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" Sing Up")
                        }
                    },
                    onClick = {
                        //navigateTo(SING_UP, false)
                    }
                )
            }
        }
        ErrorSnackBar()

        Spacer(modifier = Modifier.height(16.dp))

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