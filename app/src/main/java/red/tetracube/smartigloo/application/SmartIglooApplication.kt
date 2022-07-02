package red.tetracube.smartigloo.application

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import red.tetracube.smartigloo.definitions.NavigationIconType
import red.tetracube.smartigloo.ui.theme.SmartIglooTheme
import red.tetracube.smartigloo.R
import red.tetracube.smartigloo.application.models.TopAppBarModelState

@Composable
fun SmartIglooApplication(
    smartIglooApplicationViewModel: SmartIglooApplicationViewModel = viewModel()
) {
    val navController = rememberNavController()
    val topAppBarModelState = smartIglooApplicationViewModel.topAppBarData.collectAsState().value

    SmartIglooApplicationView(
        navController,
        topAppBarModelState,
        smartIglooApplicationViewModel::updateTopAppBarState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartIglooApplicationView(
    navController: NavHostController,
    topAppBarState: TopAppBarModelState,
    topAppBarStateSetter: (String?, NavigationIconType?, Boolean?) -> Unit
) {
    SmartIglooTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        topAppBarState,
                        //onBackPressed
                    )
                }
            ) {
                Text(text = "Hello World", modifier = Modifier.padding(it))
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
    topAppBarState: TopAppBarModelState,
) {
    val navigationIcon = when (topAppBarState.navigationIconType) {
        NavigationIconType.BACK -> R.drawable.round_arrow_back_black_24
        NavigationIconType.CLOSE -> R.drawable.round_close_black_24
    }
    CenterAlignedTopAppBar(
        title = { Text(topAppBarState.screenTitle ?: stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (topAppBarState.navigationIconVisible) {
                IconButton(onClick = {/* onBackPressed()*/ }) {
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