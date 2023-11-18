package co.cimarrones.bodega.main.layout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import co.cimarrones.bodega.login.TokenService
import co.cimarrones.bodega.main.AppNavHost
import co.cimarrones.bodega.main.Screen
import co.cimarrones.bodega.ui.theme.BodegaTheme
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val navController = rememberNavController()

    val openDrawer = fun() {
        scope.launch {
            drawerState.open()
        }
    }

    val navigateToAndCloseDrawer = fun(to: String) {
        if (to == Screen.LogOut.route) {
            TokenService.redirectToLogin(context)
        } else {
            navController.navigate(to) {
                navController.popBackStack()
            }
            scope.launch {
                drawerState.close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MainLayoutDrawer(navigateToAndCloseDrawer = navigateToAndCloseDrawer) },
        content = {
            Scaffold(
                topBar = { MainLayoutTopBar(openDrawer = openDrawer) },
                content = { padding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(padding)
                    )
                }
            )
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayoutTopBar(openDrawer: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .testTag("MSTopAppBar"),
        navigationIcon = {
            IconButton(
                modifier = Modifier.testTag("MSMenuIcon"),
                onClick = { openDrawer() },
                content = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Main Screen Menu Icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        },
        title = { Text(text = "Hi", color = MaterialTheme.colorScheme.onPrimary) })
}

@Preview(device = Devices.PHONE)
@Composable
fun PreviewMainLayoutTopBar() {
    BodegaTheme {
        MainLayoutTopBar {}
    }
}

