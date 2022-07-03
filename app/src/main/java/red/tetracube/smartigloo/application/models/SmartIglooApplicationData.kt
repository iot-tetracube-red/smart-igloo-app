package red.tetracube.smartigloo.application.models

import java.util.*

data class SmartIglooApplicationData(
    val applicationInitialized: Boolean? = null,
    val apiBaseURL: String? = null,
    val webSocketURL: String? = null,
    val authToken: String? = null,
    val nestId: UUID? = null,
    val nestName: String? = null
)