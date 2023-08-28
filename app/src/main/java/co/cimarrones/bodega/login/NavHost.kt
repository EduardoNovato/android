package co.cimarrones.bodega.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.cimarrones.bodega.login.login.LoginFormUI
import co.cimarrones.bodega.login.signUp.RegistrationScreen
import co.cimarrones.bodega.main.MainActivity
import co.cimarrones.bodega.main.details.WelcomeScreen

const val LOGIN = "login"
const val SING_UP = "sign-up"
const val WELKON = "welkon"
const val LOGOUT = "logout"


@Composable
fun AppNavHost(navController: NavHostController) {

    val navigateTo = fun(to: String, cleanHistory: Boolean?) {
        navController.navigate(to) {
            if (cleanHistory == true) {
                navController.popBackStack()
            }
        }
    }

    val context = LocalContext.current as MainActivity

    NavHost(navController = navController, startDestination = LOGIN) {

        composable(LOGIN) {
            LoginFormUI(vm = context.vm)
        }

        composable(SING_UP) {
            RegistrationScreen(vm = context.vmSignUp, navigateTo = navigateTo)
        }

    }
}

