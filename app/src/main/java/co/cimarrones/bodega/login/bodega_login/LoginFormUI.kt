package co.cimarrones.bodega.login.bodega_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.cimarrones.bodega.R
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
            modifier = composeModifier("dni")
                .onFocusChanged { vm.onDniFocusChange(it) },
            onValueChange = { vm.onDniChange(it) },
            value = uiState.dni,
            label = { Text("DNI") },
            isError = uiState.dniError,
        )

        OutlinedTextField(
            modifier = composeModifier("password")
                .onFocusChanged { vm.onPasswordChange(it) },
            value = uiState.password,
            onValueChange = { vm.onPasswordChange(it) },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
        )

        Button(
            modifier = composeModifier("submit-button")
                .height(50.dp),
            onClick = { },
            shape = Shapes.large,
            content = { Text(text = "Login") }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PrevLoginFormUI() {
    LoginFormUI()
}