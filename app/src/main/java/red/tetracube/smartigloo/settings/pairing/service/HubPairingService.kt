package red.tetracube.smartigloo.settings.pairing.service

import red.tetracube.smartigloo.settings.pairing.service.payloads.HubPairingRequest
import red.tetracube.smartigloo.settings.pairing.service.payloads.HubPairingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HubPairingService {
    @POST("/settings/hub/pair")
    fun hubPairing(@Body hubPairingRequest: HubPairingRequest): Call<HubPairingResponse>
}