package red.tetracube.smartigloo.settings.pairing.service.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class HubPairingRequest @JsonCreator constructor(
    @JsonProperty("username")
    val username: String,

    @JsonProperty("password")
    val password: String
)