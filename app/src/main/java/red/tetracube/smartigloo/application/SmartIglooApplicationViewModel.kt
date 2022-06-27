package red.tetracube.smartigloo.application

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import red.tetracube.smartigloo.application.models.SmartIglooApplicationData
import red.tetracube.smartigloo.application.models.SmartIglooUIData

class SmartIglooApplicationViewModel : ViewModel() {

    private val _smartIglooUIData = MutableStateFlow(SmartIglooUIData())
    val smartIglooUIData: StateFlow<SmartIglooUIData> get() = _smartIglooUIData

    private val _smartIglooApplicationData = MutableStateFlow(SmartIglooApplicationData())
    val smartIglooApplicationData: StateFlow<SmartIglooApplicationData> get() = _smartIglooApplicationData

}