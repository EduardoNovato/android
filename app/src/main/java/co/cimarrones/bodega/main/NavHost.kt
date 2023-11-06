package co.cimarrones.bodega.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.cimarrones.bodega.login.login.LoginFormUI
import co.cimarrones.bodega.login.signUp.RegistrationScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("sign-up")
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current as MainActivity

    NavHost(navController = navController, startDestination = Screen.Welcome.route, modifier = modifier) {

        composable(Screen.Login.route) {
            LoginFormUI( navigateTo = { navController.navigate(Screen.SignUp.route) }, vm = context.vmLogin )
        }
        composable(Screen.SignUp.route) {
            RegistrationScreen(vm = context.vmSignUp, navigateTo = { navController.navigate(route=Screen.Login.route) })
        }
    }
}
