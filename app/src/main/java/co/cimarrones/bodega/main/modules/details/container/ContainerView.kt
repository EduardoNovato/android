package co.cimarrones.bodega.main.modules.details.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ContainerView(vm: ContainerViewModel = hiltViewModel()){
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(true) {
        vm.getContainers()
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Divider(color = Color.Black)

            if (uiState.containerList.isNotEmpty()) {
                ContainerList(containerList = uiState.containerList)
            }

            Button(onClick = { /* Aquí va la acción del botón */ }) {
                Text("Agregar")
            }

        }

        if (uiState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .testTag("loading-animation")
                    .height(75.dp)
                    .width(75.dp)
                    .align(Alignment.Center),
                strokeWidth = 6.dp
            )
        }
    }
}

