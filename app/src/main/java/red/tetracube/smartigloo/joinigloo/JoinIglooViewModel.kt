package red.tetracube.smartigloo.joinigloo

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import red.tetracube.smartigloo.clients.ApiClient
import red.tetracube.smartigloo.clients.joinigloohub.JoinIglooHubService
import red.tetracube.smartigloo.clients.joinigloohub.payloads.JoinIglooHubRequest
import red.tetracube.smartigloo.core.data.settingsDataStore
import red.tetracube.smartigloo.definitions.HubJoinFields
import red.tetracube.smartigloo.definitions.ServiceConnectionStatus
import red.tetracube.smartigloo.joinigloo.models.JoinIglooViewData
import red.tetracube.smartigloo.settings.PairedNest
import retrofit2.awaitResponse
import java.net.URI

class JoinIglooViewModel : ViewModel() {

    private val _joinIglooViewData = MutableStateFlow(JoinIglooViewData())
    val joinIglooViewData: StateFlow<JoinIglooViewData> get() = _joinIglooViewData

    private val _serviceConnectionStatus = MutableStateFlow(ServiceConnectionStatus.IDLE)
    val serviceConnectionStatus: StateFlow<ServiceConnectionStatus> get() = _serviceConnectionStatus

    private val _submitButtonEnabled = MutableStateFlow(false)
    val submitButtonEnabled: StateFlow<Boolean> get() = _submitButtonEnabled

    fun updateFormFieldsValues(value: String, field: HubJoinFields) {
        when (field) {
            HubJoinFields.IGLOO_ADDRESS -> {
                _joinIglooViewData.value = _joinIglooViewData.value.copy(
                    nestAddress = value
                )
            }
            HubJoinFields.USERNAME -> {
                _joinIglooViewData.value = _joinIglooViewData.value.copy(
                    username = value
                )
            }
            HubJoinFields.PASSWORD -> {
                _joinIglooViewData.value = _joinIglooViewData.value.copy(
                    password = value
                )
            }
        }

        _submitButtonEnabled.value = !_joinIglooViewData.value.password.isNullOrEmpty()
                && !_joinIglooViewData.value.username.isNullOrEmpty()
                && !_joinIglooViewData.value.nestAddress.isNullOrEmpty()
    }

    suspend fun saveNest(context: Context) {
        val apiBaseURL = URI(
            "http",
            null,
            _joinIglooViewData.value.nestAddress,
            8080,
            null,
            null,
            null
        )
        val webSocketURL = URI(
            "ws",
            null,
            _joinIglooViewData.value.nestAddress,
            8081,
            null,
            null,
            null
        )

        setServiceStatus(ServiceConnectionStatus.CONNECTING)
        val apiBaseURLString = apiBaseURL.toString()

        val joinIglooHubService: JoinIglooHubService = ApiClient(apiBaseURLString)
            .retrofit
            .create(JoinIglooHubService::class.java)

        val configNestResponseBody = try {
            val username = _joinIglooViewData.value.username!!
            val password = _joinIglooViewData.value.password!!
            val joinIglooHubRequest = joinIglooHubService.configureNest(
                JoinIglooHubRequest(username, password)
            )
                .awaitResponse()
            if (joinIglooHubRequest.code() == 401) {
                setServiceStatus(ServiceConnectionStatus.UNAUTHORIZED)
                null
            } else {
                setServiceStatus(ServiceConnectionStatus.IDLE)
                joinIglooHubRequest.body()
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
                        .setUsername(_joinIglooViewData.value.username)
                        .setApiBaseUrl(apiBaseURLString)
                        .setAuthToken(configNestResponseBody.authenticationToken)
                        .setNestId(configNestResponseBody.nestId.toString())
                        .build()
                )
                .build()
        }
        setServiceStatus(ServiceConnectionStatus.CONNECTION_SUCCESS)
    }

    fun setServiceStatus(newStatus: ServiceConnectionStatus) {
        _serviceConnectionStatus.value = newStatus
    }

}