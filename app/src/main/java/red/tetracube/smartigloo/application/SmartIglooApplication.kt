package red.tetracube.smartigloo.application

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import red.tetracube.smartigloo.definitions.NavigationIconType
import red.tetracube.smartigloo.ui.theme.SmartIglooTheme
import red.tetracube.smartigloo.R
import red.tetracube.smartigloo.application.models.SmartIglooApplicationData
import red.tetracube.smartigloo.application.models.TopAppBarModelState
import red.tetracube.smartigloo.definitions.RouteDefinitions
import red.tetracube.smartigloo.igloo.home.IglooHomePage
import red.tetracube.smartigloo.settings.core.settingsDataStore
import red.tetracube.smartigloo.settings.pairing.HubPairingPage
import red.tetracube.smartigloo.shared.LoaderOverlay

@Composable
fun SmartIglooApplication(
    smartIglooApplicationViewModel: SmartIglooApplicationViewModel = viewModel()
) {
    val appContext = LocalContext.current
    val dataStore = appContext.settingsDataStore

    val navController = rememberNavController()
    val topAppBarModelState = smartIglooApplicationViewModel.topAppBarData.collectAsState().value
    val smartIglooApplicationData =
        smartIglooApplicationViewModel.smartIglooApplicationData.collectAsState().value

    LaunchedEffect(Unit) {
        smartIglooApplicationViewModel.loadApplicationSettings(dataStore)
    }

    SmartIglooApplicationView(
        navController,
        topAppBarModelState,
        smartIglooApplicationViewModel::updateTopAppBarState,
        smartIglooApplicationData,
        navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartIglooApplicationView(
    navController: NavHostController,
    topAppBarState: TopAppBarModelState,
    topAppBarStateSetter: (String?, NavigationIconType?, Boolean?) -> Unit,
    smartIglooApplicationData: SmartIglooApplicationData,
    onBackPressed: () -> Unit
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
                        onBackPressed
                    )
                }
            ) {
                ApplicationContent(
                    it,
                    navController,
                    smartIglooApplicationData,
                    topAppBarStateSetter
                )
            }
        }
    }
}

@Composable
fun TopAppBar(
    topAppBarState: TopAppBarModelState,
    onBackPressed: () -> Unit
) {
    val navigationIcon = when (topAppBarState.navigationIconType) {
        NavigationIconType.BACK -> R.drawable.round_arrow_back_black_24
        NavigationIconType.CLOSE -> R.drawable.round_close_black_24
    }
    CenterAlignedTopAppBar(
        title = { Text(topAppBarState.screenTitle ?: stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (topAppBarState.navigationIconVisible) {
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

@Composable
fun ApplicationContent(
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    smartIglooApplicationData: SmartIglooApplicationData,
    appBarStateSetter: (String?, NavigationIconType?, Boolean?) -> Unit
    /*,
    cosyNestAppData: CosyNestAppViewData,
    setNavigationIconVisible: (Boolean) -> Unit,
    screenTitleSetter: (String?) -> Unit*/
) {
    NavHost(
        navController = navHostController,
        startDestination = RouteDefinitions.HOME,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(RouteDefinitions.HOME) {
            if (smartIglooApplicationData.applicationInitialized == null) {
                LoaderOverlay()
                return@composable
            }
            IglooHomePage(
                smartIglooApplicationData.applicationInitialized,
                navHostController
            )

            /*else {
                setNavigationIconVisible(false)
                screenTitleSetter(null)
                val nestFeaturesViewModel: NestFeaturesViewModel = viewModel(
                    checkNotNull(LocalViewModelStoreOwner.current),
                    null,
                    NestFeaturesViewModelFactory(
                        cosyNestAppData.nestName!!,
                        cosyNestAppData.authToken!!,
                        cosyNestAppData.nestId!!,
                        cosyNestAppData.apiBaseURL!!,
                        cosyNestAppData.webSocketURL!!
                    )
                )
                NestFeatures(nestFeaturesViewModel, navHostController)
            }*/
        }
        composable(RouteDefinitions.HUB_PAIRING_SETTINGS) {
            appBarStateSetter(
                stringResource(id = R.string.hub_pairing),
                NavigationIconType.BACK,
                true
            )
            /*
             val configNestViewModel: ConfigureNestViewModel = viewModel()
             ConfigureNestSettings(configNestViewModel, navHostController)*/
            HubPairingPage(
                navHostController
            )
        }
        composable(RouteDefinitions.APPLIANCE_DETAILS) {
            //  setNavigationIconVisible(true)
            //  screenTitleSetter(it.arguments?.getString("featureName"))
            /*     val viewModel: FeatureDetailsViewModel = viewModel(
                     factory = FeatureDetailsViewModelFactory(
                         featureId = UUID.fromString(it.arguments!!.getString("featureId")!!),
                         featureType = FeatureTypeEnum.valueOf(it.arguments!!.getString("featureType")!!)
                     )
                 )
                 FeatureDetails(featureDetailsViewModel = viewModel)*/
        }
    }
}