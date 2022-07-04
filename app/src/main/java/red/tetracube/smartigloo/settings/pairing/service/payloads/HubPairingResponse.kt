package red.tetracube.smartigloo.settings.pairing.service.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class HubPairingResponse @JsonCreator constructor (
    @JsonProperty("igloo_id")
    val nestId: UUID,

    @JsonProperty("igloo_name")
    val nestName: String,

    @JsonProperty("authentication_token")
    val authenticationToken: String
)