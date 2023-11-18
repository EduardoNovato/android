package co.cimarrones.bodega.main.modules.details.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ContainerList(containerList: List<Container>){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)){
        items(
            items = containerList,
            itemContent = {
                ContainerCard(item = it)
            }
        )
    }
}

@Composable
@Preview(device = Devices.PHONE)
fun PreviewPOWorkStepsList() {
    val listOfContainers = mutableListOf<Container>()
    for (i in 1..5) {
        listOfContainers.add(
            Container(
                id = "",
                name = "caja",
                typeContainer = "caja",
                isSeal = true
            )
        )
    }
    ContainerList(listOfContainers)
}
