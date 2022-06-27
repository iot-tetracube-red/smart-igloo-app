package red.tetracube.smartigloo.application

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import red.tetracube.smartigloo.definitions.NavigationIconType
import red.tetracube.smartigloo.ui.theme.SmartIglooTheme
import red.tetracube.smartigloo.R

@Composable
fun SmartIglooApplication(
    smartIglooApplicationViewModel: SmartIglooApplicationViewModel = viewModel()
) {
    val navController = rememberNavController()

    SmartIglooApplicationView(
        navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartIglooApplicationView(
    navController: NavHostController
) {
    SmartIglooTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        cosyNestAppData.navigationIconVisible,
                        cosyNestAppData.navigationIconType,
                        cosyNestAppData.screenTitle,
                        onBackPressed
                    )
                }
            ) {
               // Text(text = "Hello World")
               /* MainNavigation(
                    it,
                    navController,
                    cosyNestAppData,
                    setNavigationIconVisible,
                    setTitle
                )*/
            }
        }
    }
}

@Composable
fun TopAppBar(
    showBackNavigation: Boolean,
    navigationIconType: NavigationIconType,
    title: String?,
    onBackPressed: () -> Unit
) {
    val navigationIcon = when (navigationIconType) {
        NavigationIconType.BACK -> R.drawable.round_arrow_back_black_24
        NavigationIconType.CLOSE -> R.drawable.round_close_black_24
    }
    CenterAlignedTopAppBar(
        title = { Text(title ?: stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (showBackNavigation) {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        painter = painterResource(id = navigationIcon),
                        contentDescription = ""
                    )
                }
            }
        },
        actions = {
        }
    )
}