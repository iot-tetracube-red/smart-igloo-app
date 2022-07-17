package red.tetracube.smartigloo.settings.pairing.service.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class HubPairingResponse @JsonCreator constructor (
    @JsonProperty("hub_id")
    val hubId: UUID,

    @JsonProperty("hub_name")
    val hubName: String,

    @JsonProperty("authentication_token")
    val authenticationToken: String
)