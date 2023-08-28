package co.cimarrones.bodega.login.login

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import co.cimarrones.bodega.main.NetworkMonitor
import co.cimarrones.bodega.ui.theme.BodegaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginUIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: LoginUIViewModel by viewModels()
        // to be able to listen for changes in the network connectivity status
        val networkMonitor = NetworkMonitor()
        val req = networkMonitor.buildNetworkRequestObject()
        val callback = networkMonitor.buildNetworkCallbackObject { connected: Boolean ->
            vm.onNetworkConnectionChange(connected)
        }
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(req, callback)


        setContent {

            BodegaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginFormUI(vm)
                }
            }
        }
    }
}