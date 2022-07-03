package red.tetracube.smartigloo.application

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import red.tetracube.smartigloo.application.models.SmartIglooApplicationData
import red.tetracube.smartigloo.application.models.TopAppBarModelState
import red.tetracube.smartigloo.definitions.NavigationIconType
import red.tetracube.smartigloo.settings.SmartIglooSettings
import java.util.*

class SmartIglooApplicationViewModel : ViewModel() {

    private val _smartIglooApplicationData = MutableStateFlow(SmartIglooApplicationData())
    val smartIglooApplicationData: StateFlow<SmartIglooApplicationData> get() = _smartIglooApplicationData

    private val _topAppBarData = MutableStateFlow(TopAppBarModelState())
    val topAppBarData: StateFlow<TopAppBarModelState> get() = _topAppBarData

    fun updateTopAppBarState(title: String?, navigationIconType: NavigationIconType?, navigationIconVisible: Boolean?) {
        _topAppBarData.value = topAppBarData.value.copy(
            screenTitle = title,
            navigationIconType = navigationIconType ?: topAppBarData.value.navigationIconType,
            navigationIconVisible = navigationIconVisible ?: topAppBarData.value.navigationIconVisible
        )
    }

    suspend fun loadApplicationSettings(dataStore: DataStore<SmartIglooSettings>) {
        dataStore.data.map { applicationSettingsData ->
            val currentServer = applicationSettingsData
                .connectedNestsOrBuilderList
                .firstOrNull { nest -> nest.currentServer }
                ?: return@map _smartIglooApplicationData.value.copy(
                    applicationInitialized = false
                )
            return@map _smartIglooApplicationData.value.copy(
                applicationInitialized = applicationSettingsData.isInitialized,
                apiBaseURL = currentServer.apiBaseUrl,
                authToken = currentServer.authToken,
                nestId = UUID.fromString(currentServer.nestId),
                nestName = currentServer.alias
            )
        }
            .collect { _smartIglooApplicationData.value = it }
    }

}