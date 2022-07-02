package red.tetracube.smartigloo.application

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import red.tetracube.smartigloo.application.models.SmartIglooApplicationData
import red.tetracube.smartigloo.application.models.SmartIglooUIData
import red.tetracube.smartigloo.application.models.TopAppBarModelState
import red.tetracube.smartigloo.definitions.NavigationIconType

class SmartIglooApplicationViewModel : ViewModel() {

    private val _smartIglooUIData = MutableStateFlow(SmartIglooUIData())
    val smartIglooUIData: StateFlow<SmartIglooUIData> get() = _smartIglooUIData

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

}