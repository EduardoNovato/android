package co.cimarrones.bodega.login.bodega_login

import android.content.Intent
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
import androidx.lifecycle.Observer
import co.cimarrones.bodega.login.utils.TokenService
import co.cimarrones.bodega.main.MainActivity
import co.cimarrones.bodega.main.NetworkMonitor
import co.cimarrones.bodega.ui.theme.BodegaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginUIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: LoginUIViewModel by viewModels()

        // to activate observer if there is a token in shared preferences
        val currentToken = TokenService.getToken(applicationContext)
        if (currentToken.isNotEmpty()) vm.setToken(currentToken)
        // to be able to listen for changes in the network connectivity status
        val networkMonitor = NetworkMonitor()
        val req = networkMonitor.buildNetworkRequestObject()
        val callback = networkMonitor.buildNetworkCallbackObject { connected: Boolean ->
            vm.onNetworkConnectionChange(connected)
        }
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(req, callback)
        // Setting up token observable
        val tokenObserver = Observer<String> { token ->
            if (TokenService.isTokenValid(token)) {
                TokenService.setToken(applicationContext, token)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        vm.token.observe(this, tokenObserver)

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