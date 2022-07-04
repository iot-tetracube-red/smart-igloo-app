package red.tetracube.smartigloo.joinigloo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun JoinIglooPage(
    viewModel: JoinIglooViewModel,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val configureNestDataData = viewModel.joinIglooViewData.collectAsState().value
    val submitButtonEnabled = viewModel.submitButtonEnabled.collectAsState().value
    val connectionStatus = viewModel.serviceConnectionStatus.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    /*JoinIglooView(
        configureNestDataData,
        submitButtonEnabled,
        connectionStatus,
        { value, field -> viewModel.updateFormFieldsValues(value, field) },
        {
            coroutineScope.launch {
                viewModel.saveNest(context)
            }
        },
        {
            if (it == ServiceConnectionStatus.CONNECTION_SUCCESS) {
                navHostController.navigate(HOME)
            }
            viewModel.setServiceStatus(ServiceConnectionStatus.IDLE)
        }
    )*/
}