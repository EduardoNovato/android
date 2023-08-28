package de.deeping.paperless.main.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.cimarrones.bodega.R
import co.cimarrones.bodega.login.LOGOUT
import co.cimarrones.bodega.ui.theme.BodegaTheme

@Preview(showBackground = true)
@Composable
fun PreviewOfMainLayoutDrawer() {
    BodegaTheme {
        MainLayoutDrawer(navigateToAndCloseDrawer = {})
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayoutDrawer(navigateToAndCloseDrawer: (String) -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier.testTag("MSDrawer")
    ) {
        MainLayoutDrawerHeader()
        MSDNavigationOptions(navigateToAndCloseDrawer)
    }
}

@Composable
fun MainLayoutDrawerHeader() {
    //val email = TokenService.getEmailFromToken()
    Row(
        modifier = Modifier
            .testTag("DrawerHeader")
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "loco", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp
            )
        }
    }
}

@Composable
fun MSDNavigationOptions(navigateToAndCloseDrawer: (String) -> Unit) {
    for (opt in buildNavOptions()) {
        TextButton(
            modifier = Modifier
                .testTag(opt.testTag)
                .padding(24.dp, 4.dp),
            onClick = { navigateToAndCloseDrawer(opt.destination) }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = opt.icon,
                    contentDescription = opt.iconContentDescription,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(end = 8.dp)
                )
                Text(text = stringResource(id = opt.stringId), fontSize = 20.sp)
            }
        }
    }

}

data class NavOption(
    val testTag: String,
    val icon: ImageVector,
    val iconContentDescription: String,
    val destination: String,
    val stringId: Int
)

fun buildNavOptions(): List<NavOption> = listOf(
    NavOption(
        "LogoutLink",
        Icons.Filled.ExitToApp,
        "Link to navigate to login View @logout",
        LOGOUT,
        R.string.email_label
    ),
)
