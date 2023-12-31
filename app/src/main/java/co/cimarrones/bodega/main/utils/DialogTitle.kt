package co.cimarrones.bodega.main.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DialogTitle(testTag: String, text: String) {
    Text(
        text = text,
        modifier = Modifier
            .testTag(testTag)
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black
    )
}