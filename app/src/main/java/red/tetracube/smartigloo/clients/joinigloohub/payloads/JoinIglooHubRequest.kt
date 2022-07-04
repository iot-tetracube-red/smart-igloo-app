package red.tetracube.smartigloo.clients.joinigloohub.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class JoinIglooHubRequest @JsonCreator constructor(
    @JsonProperty("username")
    val username: String,

    @JsonProperty("password")
    val password: String
)