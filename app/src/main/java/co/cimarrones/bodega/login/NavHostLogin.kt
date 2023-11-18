package co.cimarrones.bodega.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.cimarrones.bodega.login.login.LoginFormUI
import co.cimarrones.bodega.login.login.LoginUIActivity
import co.cimarrones.bodega.login.signUp.RegistrationScreen
import co.cimarrones.bodega.main.MainActivity

sealed class ScreenLogin(val route: String) {
    object Login : ScreenLogin("login")
    object SignUp : ScreenLogin("sign-up")
}

@Composable
fun AppNavHostLogin(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current as LoginUIActivity

    NavHost(navController = navController, startDestination = ScreenLogin.Login.route, modifier = modifier) {

        composable(ScreenLogin.Login.route) {
            LoginFormUI( navigateTo = { navController.navigate(ScreenLogin.SignUp.route) }, vm = context.vmLogin )
        }

        composable(ScreenLogin.SignUp.route) {
            RegistrationScreen(vm = context.vmSignUp, navigateTo = { navController.navigate(route=ScreenLogin.Login.route) })
        }
    }
}
