package co.cimarrones.bodega.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.cimarrones.bodega.main.modules.details.WelcomeScreen
import co.cimarrones.bodega.main.modules.details.container.ContainerView


sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Container : Screen("container")
    object LogOut: Screen("log-out")
}
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(navController = navController, startDestination = Screen.Welcome.route, modifier = modifier) {
        composable(Screen.Welcome.route) {
            WelcomeScreen()
        }
        composable(Screen.Container.route){
            ContainerView()
        }
    }
}