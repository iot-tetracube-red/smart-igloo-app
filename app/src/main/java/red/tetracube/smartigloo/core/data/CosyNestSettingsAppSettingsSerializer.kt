package red.tetracube.smartigloo.core.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import red.tetracube.smartigloo.settings.SmartIglooSettings
import java.io.InputStream
import java.io.OutputStream

object CosyNestSettingsAppSettingsSerializer : Serializer<SmartIglooSettings> {

    override val defaultValue: SmartIglooSettings = SmartIglooSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SmartIglooSettings {
        try {
            return SmartIglooSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: SmartIglooSettings,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<SmartIglooSettings> by dataStore(
    fileName = "settings.pb",
    serializer = CosyNestSettingsAppSettingsSerializer
)