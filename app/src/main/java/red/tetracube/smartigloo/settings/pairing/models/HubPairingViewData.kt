package red.tetracube.smartigloo.settings.pairing.models

data class HubPairingViewData(
    val apiHubAddress: String? = null,
    val apiHubAddressTouched: Boolean = false,
    val apiHubAddressHasError: Boolean? = null,
    val webSocketHubAddress: String? = null,
    val webSocketHubTouched: Boolean = false,
    val webSocketHubAddressHasError: Boolean? = null,
    val username: String? = null,
    val usernameTouched: Boolean = false,
    val usernameHasError: Boolean? = null,
    val password: String? = null,
    val passwordTouched: Boolean = false,
    val passwordHasError: Boolean? = null,
)