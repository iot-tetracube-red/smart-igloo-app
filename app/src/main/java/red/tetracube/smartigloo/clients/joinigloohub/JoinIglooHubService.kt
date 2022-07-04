package red.tetracube.smartigloo.clients.joinigloohub

import red.tetracube.smartigloo.clients.joinigloohub.payloads.JoinIglooHubRequest
import red.tetracube.smartigloo.clients.joinigloohub.payloads.JoinIglooHubResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinIglooHubService {
    @POST("/settings/join-igloo-hub")
    fun configureNest(@Body joinIglooHubRequest: JoinIglooHubRequest): Call<JoinIglooHubResponse>
}