package co.cimarrones.bodega.main

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import co.cimarrones.bodega.login.login.LoginUIViewModel
import co.cimarrones.bodega.login.signUp.SignUpViewModel
import co.cimarrones.bodega.main.layout.MainLayout
import co.cimarrones.bodega.ui.theme.BodegaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val vm: LoginUIViewModel by viewModels()
    val vmSignUp: SignUpViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkMonitor = NetworkMonitor()

        val req = networkMonitor.buildNetworkRequestObject()
        val callback = networkMonitor.buildNetworkCallbackObject { connected: Boolean ->
            vm.onNetworkConnectionChange(connected)
        }
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(req, callback)

        setContent {
            val navController = rememberNavController()
            BodegaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //AppNavHost(navController)
                    MainLayout()
                }
            }
        }
    }
}
