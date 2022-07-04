package red.tetracube.smartigloo.clients

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ApiClient(baseUrl: String) {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
}