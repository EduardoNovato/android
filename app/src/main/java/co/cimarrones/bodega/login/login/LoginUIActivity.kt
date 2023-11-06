package co.cimarrones.bodega.login.login

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.navigation.compose.rememberNavController
import co.cimarrones.bodega.login.TokenService
import co.cimarrones.bodega.main.MainActivity
import co.cimarrones.bodega.main.NetworkMonitor
import co.cimarrones.bodega.main.Screen
import co.cimarrones.bodega.ui.theme.BodegaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginUIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: LoginUIViewModel by viewModels()
        val tokenService = TokenService(this)
        val currentToken = tokenService.getToken()
        if (currentToken.isNotEmpty()) vm.setToken(currentToken)
        val networkMonitor = NetworkMonitor()
        val req = networkMonitor.buildNetworkRequestObject()
        val callback = networkMonitor.buildNetworkCallbackObject { connected: Boolean ->
            vm.onNetworkConnectionChange(connected)
        }
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(req, callback)

        val tokenObserver = Observer<String> { token ->
            if (token.isNotEmpty()) {
                tokenService.setToken(token)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        vm.token.observe(this, tokenObserver)

        setContent {
            val navController = rememberNavController()
            BodegaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginFormUI({ navController.navigate(Screen.SignUp.route) }, vm)
                }
            }
        }
    }
}