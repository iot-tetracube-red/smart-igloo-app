package red.tetracube.smartigloo.settings.pairing

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import red.tetracube.smartigloo.clients.ApiClient
import red.tetracube.smartigloo.definitions.HubPairingFields
import red.tetracube.smartigloo.definitions.ServiceConnectionStatus
import red.tetracube.smartigloo.settings.PairedNest
import red.tetracube.smartigloo.settings.core.settingsDataStore
import red.tetracube.smartigloo.settings.pairing.models.HubPairingViewData
import red.tetracube.smartigloo.settings.pairing.service.HubPairingService
import red.tetracube.smartigloo.settings.pairing.service.payloads.HubPairingRequest
import retrofit2.awaitResponse
import java.net.URI

class HubPairingViewModel : ViewModel() {

    private val _hubPairingViewData = MutableStateFlow(HubPairingViewData())
    val hubPairingViewData: StateFlow<HubPairingViewData> get() = _hubPairingViewData

    private val _serviceConnectionStatus = MutableStateFlow(ServiceConnectionStatus.IDLE)
    val serviceConnectionStatus: StateFlow<ServiceConnectionStatus> get() = _serviceConnectionStatus

    private val _submitButtonEnabled = MutableStateFlow(false)
    val submitButtonEnabled: StateFlow<Boolean> get() = _submitButtonEnabled

    fun updateFormFieldsValues(value: String, field: HubPairingFields) {
        when (field) {
            HubPairingFields.API_HUB_ADDRESS -> {
                _hubPairingViewData.value = _hubPairingViewData.value.copy(
                    apiHubAddress = value,
                    apiHubAddressTouched = true,
                    apiHubAddressHasError = _hubPairingViewData.value.apiHubAddress.isNullOrEmpty() ||
                            try {
                                URI(value).toURL()
                                false
                            } catch (_: Exception) {
                                true
                            }
                )
            }
            HubPairingFields.WEB_SOCKET_HUB_ADDRESS -> {
                _hubPairingViewData.value = _hubPairingViewData.value.copy(
                    webSocketHubAddress = value,
                    webSocketHubTouched = true,
                    webSocketHubAddressHasError = _hubPairingViewData.value.webSocketHubAddress.isNullOrEmpty() ||
                            try {
                                URI(value).scheme != "ws" && URI(value).scheme != "wss" && URI(value).host.isNullOrEmpty()
                            } catch (_: Exception) {
                                true
                            }
                )
            }
            HubPairingFields.USERNAME -> {
                _hubPairingViewData.value = _hubPairingViewData.value.copy(
                    username = value,
                    usernameTouched = true,
                    usernameHasError = value.isEmpty()
                )
            }
            HubPairingFields.PASSWORD -> {
                _hubPairingViewData.value = _hubPairingViewData.value.copy(
                    password = value,
                    passwordTouched = true,
                    passwordHasError = value.isEmpty()
                )
            }
        }
        _submitButtonEnabled.value =
            _hubPairingViewData.value.passwordTouched && !(_hubPairingViewData.value.passwordHasError
                ?: true)
                    && _hubPairingViewData.value.usernameTouched && !(_hubPairingViewData.value.usernameHasError
                ?: true)
                    && _hubPairingViewData.value.apiHubAddressTouched && !(_hubPairingViewData.value.apiHubAddressHasError
                ?: true)
                    && _hubPairingViewData.value.webSocketHubTouched && !(_hubPairingViewData.value.webSocketHubAddressHasError
                ?: true)
    }

    suspend fun doPairing(context: Context) {
        setServiceStatus(ServiceConnectionStatus.CONNECTING)
        val apiBaseURLString = hubPairingViewData.value.apiHubAddress!!
        val webSocketBaseUrlString = hubPairingViewData.value.webSocketHubAddress!!
        val username = _hubPairingViewData.value.username!!

        val hubPairingService: HubPairingService = ApiClient(apiBaseURLString)
            .retrofit
            .create(HubPairingService::class.java)

        val configNestResponseBody = try {
            val password = _hubPairingViewData.value.password!!
            val hubPairingResponse = hubPairingService.hubPairing(
                HubPairingRequest(username, password)
            )
                .awaitResponse()
            if (hubPairingResponse.code() == 401) {
                setServiceStatus(ServiceConnectionStatus.UNAUTHORIZED)
                null
            } else {
                setServiceStatus(ServiceConnectionStatus.IDLE)
                hubPairingResponse.body()
            }
        } catch (ex: Exception) {
            setServiceStatus(ServiceConnectionStatus.CONNECTION_ERROR)
            null
        } ?: return

        context.settingsDataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .addConnectedNests(
                    PairedNest.newBuilder()
                        .setCurrentServer(true)
                        .setAlias(configNestResponseBody.nestName)
                        .setUsername(username)
                        .setApiBaseUrl(apiBaseURLString)
                        .setWebSocketBaseUrl(webSocketBaseUrlString)
                        .setAuthToken(configNestResponseBody.authenticationToken)
                        .setNestId(configNestResponseBody.nestId.toString())
                        .build()
                )
                .build()
        }
        setServiceStatus(ServiceConnectionStatus.CONNECTION_SUCCESS)
    }

    private fun setServiceStatus(newStatus: ServiceConnectionStatus) {
        _serviceConnectionStatus.value = newStatus
    }

}