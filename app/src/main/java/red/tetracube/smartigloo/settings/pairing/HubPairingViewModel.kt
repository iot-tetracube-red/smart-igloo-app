package red.tetracube.smartigloo.settings.pairing

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import red.tetracube.smartigloo.clients.ApiClient
import red.tetracube.smartigloo.settings.pairing.service.HubPairingService
import red.tetracube.smartigloo.settings.pairing.service.payloads.HubPairingRequest
import red.tetracube.smartigloo.definitions.HubPairingFields
import red.tetracube.smartigloo.definitions.ServiceConnectionStatus
import red.tetracube.smartigloo.settings.pairing.models.HubPairingViewData
import red.tetracube.smartigloo.settings.PairedNest
import red.tetracube.smartigloo.settings.core.settingsDataStore
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
            HubPairingFields.IGLOO_ADDRESS -> {
                _hubPairingViewData.value = _hubPairingViewData.value.copy(
                    nestAddress = value
                )
            }
            HubPairingFields.USERNAME -> {
                _hubPairingViewData.value = _hubPairingViewData.value.copy(
                    username = value
                )
            }
            HubPairingFields.PASSWORD -> {
                _hubPairingViewData.value = _hubPairingViewData.value.copy(
                    password = value
                )
            }
        }

        _submitButtonEnabled.value = !_hubPairingViewData.value.password.isNullOrEmpty()
                && !_hubPairingViewData.value.username.isNullOrEmpty()
                && !_hubPairingViewData.value.nestAddress.isNullOrEmpty()
    }

    suspend fun saveNest(context: Context) {
        val apiBaseURL = URI(
            "http",
            null,
            _hubPairingViewData.value.nestAddress,
            8080,
            null,
            null,
            null
        )
        val webSocketURL = URI(
            "ws",
            null,
            _hubPairingViewData.value.nestAddress,
            8081,
            null,
            null,
            null
        )

        setServiceStatus(ServiceConnectionStatus.CONNECTING)
        val apiBaseURLString = apiBaseURL.toString()

        val hubPairingService: HubPairingService = ApiClient(apiBaseURLString)
            .retrofit
            .create(HubPairingService::class.java)

        val configNestResponseBody = try {
            val username = _hubPairingViewData.value.username!!
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
                        .setUsername(_hubPairingViewData.value.username)
                        .setApiBaseUrl(apiBaseURLString)
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