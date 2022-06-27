package red.tetracube.smartigloo.application.models

import red.tetracube.smartigloo.definitions.NavigationIconType

data class SmartIglooUIData(
    val applicationInitialized: Boolean? = null,
    val screenTitle: String? = null,
    val navigationIconType: NavigationIconType = NavigationIconType.BACK,
    val navigationIconVisible: Boolean = false
)