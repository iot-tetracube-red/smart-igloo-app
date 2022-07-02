package red.tetracube.smartigloo.application.models

import red.tetracube.smartigloo.definitions.NavigationIconType

data class TopAppBarModelState(
    val screenTitle: String? = null,
    val navigationIconType: NavigationIconType = NavigationIconType.BACK,
    val navigationIconVisible: Boolean = false
)