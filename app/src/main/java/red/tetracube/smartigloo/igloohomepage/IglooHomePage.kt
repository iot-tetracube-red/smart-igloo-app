package red.tetracube.smartigloo.igloohomepage

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import red.tetracube.smartigloo.R
import red.tetracube.smartigloo.definitions.RouteDefinitions
import red.tetracube.smartigloo.shared.AlertDialogView

@Composable
fun IglooHomePage(
    applicationInitialized: Boolean,
    navHostController: NavHostController,
) {
    if (!applicationInitialized) {
        ManageDialog(applicationInitialized, navHostController)
    }

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

@Composable
fun ManageDialog(
    applicationInitialized: Boolean,
    navHostController: NavHostController
) {
    if (!applicationInitialized) {
        AlertDialogView(
            iconId = R.drawable.round_touch_app_24,
            titleStringId = R.string.no_igloo_configured_dialog_title,
            textStringId = R.string.no_igloo_configured_dialog_message,
            confirmStringId = R.string.go,
            dismissible = false,
            onDismiss = {
                navHostController.navigate(RouteDefinitions.JOIN_IGLOO_HUB)
            }
        )
    }
}