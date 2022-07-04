package red.tetracube.smartigloo.settings.pairing

import androidx.compose.foundation.interaction.MutableInteractionSource
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

import red.tetracube.smartigloo.R

@Composable
fun HubPairingPage(
  //  viewModel: HubPairingViewModel,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    /*val configureNestDataData = viewModel.hubPairingViewData.collectAsState().value
    val submitButtonEnabled = viewModel.submitButtonEnabled.collectAsState().value
    val connectionStatus = viewModel.serviceConnectionStatus.collectAsState().value*/
    val coroutineScope = rememberCoroutineScope()

    HubPairingView()

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

@Composable
fun HubPairingView(
/*    configureNestDataData: ConfigureNestViewData,
    submitButtonEnabled: Boolean,
    serviceConnectionStatus: ServiceConnectionStatus,
    setFieldValue: (String, ConfigureNestFields) -> Unit,
    onButtonSaveTap: () -> Unit,
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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = stringResource(id = R.string.hub_pairing_address_label)) },
            value = "",
            onValueChange = {}
        )

        OutlinedTextField(
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            label = { Text(text = stringResource(id = R.string.hub_pairing_username_label)) },
            value = "",
            onValueChange = {}
        )

        OutlinedTextField(
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(text = stringResource(id = R.string.hub_pairing_password_label)) },
            value = "",
            onValueChange = {}
        )

        /* Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(vertical = 8.dp)
         ) {
             ElevatedButton(
                 enabled = submitButtonEnabled,
                 onClick = { onButtonSaveTap() }
             ) {
                 Text(text = stringResource(id = R.string.config_nest_save_button))
             }
         }*/
    }
}