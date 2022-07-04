package red.tetracube.smartigloo.settings.pairing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import red.tetracube.smartigloo.R
import red.tetracube.smartigloo.definitions.HubPairingFields
import red.tetracube.smartigloo.settings.pairing.models.HubPairingViewData

@Composable
fun HubPairingPage(
    hubPairingViewModel: HubPairingViewModel,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val hubPairingViewData = hubPairingViewModel.hubPairingViewData.collectAsState().value
    val submitButtonEnabled = hubPairingViewModel.submitButtonEnabled.collectAsState().value
    /*

    val connectionStatus = viewModel.serviceConnectionStatus.collectAsState().value*/
    val coroutineScope = rememberCoroutineScope()

    HubPairingView(
        hubPairingViewData,
        { value, field -> hubPairingViewModel.updateFormFieldsValues(value, field) },
        submitButtonEnabled,
        {
            coroutineScope.launch {
                hubPairingViewModel.doPairing(context)
            }
        },
    )

    /*JoinIglooView(
        configureNestDataData,
        submitButtonEnabled,
        connectionStatus,
        { value, field -> viewModel.updateFormFieldsValues(value, field) },

        {
            if (it == ServiceConnectionStatus.CONNECTION_SUCCESS) {
                navHostController.navigate(HOME)
            }
            viewModel.setServiceStatus(ServiceConnectionStatus.IDLE)
        }
    )*/
}

@Composable
fun HubPairingView(
    hubPairingViewData: HubPairingViewData,
    setFieldValue: (String, HubPairingFields) -> Unit,
    submitButtonEnabled: Boolean,
    onButtonSaveTap: () -> Unit,
/*    configureNestDataData: ConfigureNestViewData,

    serviceConnectionStatus: ServiceConnectionStatus,


    onDialogDismiss: (ServiceConnectionStatus) -> Unit*/
) {
    /*  ServiceConnectionDialog(
          serviceConnectionStatus,
          onDialogDismiss
      )*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            singleLine = true,
            maxLines = 1,
            isError = hubPairingViewData.apiHubAddressTouched && hubPairingViewData.apiHubAddressHasError ?: false,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            ),
            placeholder = { Text("http://ip_hub_address:port") },
            label = { Text(text = stringResource(id = R.string.hub_pairing_address_label)) },
            value = hubPairingViewData.apiHubAddress ?: "",
            onValueChange = { value -> setFieldValue(value, HubPairingFields.API_HUB_ADDRESS) }
        )

        OutlinedTextField(
            singleLine = true,
            maxLines = 1,
            isError = hubPairingViewData.webSocketHubTouched && hubPairingViewData.webSocketHubAddressHasError ?: false,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            ),
            placeholder = { Text("ws://ip_hub_address:port") },
            label = { Text(text = stringResource(id = R.string.hub_pairing_web_socket_address_label)) },
            value = hubPairingViewData.webSocketHubAddress ?: "",
            onValueChange = { value ->
                setFieldValue(
                    value,
                    HubPairingFields.WEB_SOCKET_HUB_ADDRESS
                )
            }
        )

        OutlinedTextField(
            singleLine = true,
            maxLines = 1,
            isError = hubPairingViewData.usernameTouched && hubPairingViewData.usernameHasError ?: false,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = stringResource(id = R.string.hub_pairing_username_label)) },
            value = hubPairingViewData.username ?: "",
            onValueChange = { value -> setFieldValue(value, HubPairingFields.USERNAME) }
        )

        OutlinedTextField(
            singleLine = true,
            maxLines = 1,
            isError = hubPairingViewData.passwordTouched && hubPairingViewData.passwordHasError ?: false,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(text = stringResource(id = R.string.hub_pairing_password_label)) },
            value = hubPairingViewData.password ?: "",
            onValueChange = { value -> setFieldValue(value, HubPairingFields.PASSWORD) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            ElevatedButton(
                enabled = submitButtonEnabled,
                onClick = { onButtonSaveTap() }
            ) {
                Text(text = stringResource(id = R.string.hub_pairing_do_pairing_button))
            }
        }
    }
}