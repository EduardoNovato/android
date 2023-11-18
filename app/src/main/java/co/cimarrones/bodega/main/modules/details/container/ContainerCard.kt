package co.cimarrones.bodega.main.modules.details.container

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.cimarrones.bodega.main.utils.LabelText
import co.cimarrones.bodega.main.utils.PrimaryTextValue

@Composable
fun ContainerCard(item: Container) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.testTag("card-wrapper")
    ) {
        Column {
            val mod = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)
            Row {
                Column(modifier = mod.weight(1f)) {
                    LabelText(testTag = "", label = "Name")
                    PrimaryTextValue(
                        testTag = "",
                        value = item.name
                    )
                }
                Column(modifier = mod.weight(1f)) {
                    LabelText(testTag = "", label = "Tipo de Contenedor")
                    PrimaryTextValue(testTag = "", value = item.typeContainer)
                }
                Column(modifier = mod.weight(1f)) {
                    LabelText(
                        testTag = "",
                        label = "Sello",
                    )
                    PrimaryTextValue(
                        testTag = "",
                        value = item.isSeal.let { if (it) "🔒" else "🔓" }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ContainerCardPreview() {
    val item = Container(
        id = "",
        name = "caja",
        typeContainer = "caja",
        isSeal = true
    )
    ContainerCard(item)
}
