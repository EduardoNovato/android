package co.cimarrones.bodega.login.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.cimarrones.bodega.R
import co.cimarrones.bodega.login.LOGIN
import co.cimarrones.bodega.main.utils.DialogTitle
import co.cimarrones.bodega.ui.theme.Shapes

@Composable
fun Logo() {
    val isDarkTheme = isSystemInDarkTheme()
    val logoResource = if (isDarkTheme) R.drawable.ic_logo_login_d else R.drawable.ic_logo_login
    Image(
        painter = painterResource(id = logoResource),
        contentDescription = "stringResource(id = R.string.logo_content_description)",
        modifier = Modifier.testTag("icon-logo-login")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    vm: SignUpViewModel = hiltViewModel(),
    navigateTo: (String, Boolean?) -> Unit
) {

    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val phoneValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    //val confirmPasswordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            DialogTitle(
                testTag = "traceability-dialog-title",
                text = "Sign Up",
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Logo()
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .testTag("")
                            .weight(1f)
                            .padding(8.dp),
                        value = nameValue.value,
                        onValueChange = {},
                        label = { Text(text = "Name") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .testTag("")
                            .weight(1f)
                            .padding(8.dp),
                        value = nameValue.value,
                        onValueChange = {},
                        label = { Text(text = "Last Name") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                    )
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = emailValue.value,
                onValueChange = { },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = passwordValue.value,
                onValueChange = {},
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                trailingIcon = {
                    /*IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                    )*//* *//*{
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password Icon"
                    )
                }*/
                },
                //visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = phoneValue.value,
                onValueChange = { },
                label = { Text(text = "Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(50.dp)
                        .testTag("dismiss-button")
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    shape = Shapes.small,
                ) {
                    Text("Sign Up", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                            append(" Log in")
                        }
                    },
                    onClick = {
                        navigateTo(LOGIN, false)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun A() {
    val fn = fun(_: String, _: Boolean?) {}
    RegistrationScreen(navigateTo = fn)
}
