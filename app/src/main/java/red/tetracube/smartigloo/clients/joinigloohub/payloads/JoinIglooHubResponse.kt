package red.tetracube.smartigloo.clients.joinigloohub.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class JoinIglooHubResponse @JsonCreator constructor (
    @JsonProperty("igloo_id")
    val nestId: UUID,

    @JsonProperty("igloo_name")
    val nestName: String,

    @JsonProperty("authentication_token")
    val authenticationToken: String
)