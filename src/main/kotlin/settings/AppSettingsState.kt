package settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.OptionTag
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@State(
    name = "settings.AppSettingsState",
    storages = [Storage("TranslationPluginSettings.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState?> {

    @Serializable
    data class ApiClientConfig(
        var id: String = "",
        var secret: String = ""
    )

    class ApiConfigConverter : Converter<ApiClientConfig?>() {
        override fun fromString(value: String): ApiClientConfig =
            Json.decodeFromString(value)

        override fun toString(value: ApiClientConfig): String =
            Json.encodeToString(value)
    }

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }

    @OptionTag(converter = ApiConfigConverter::class)
    var apiClientConfig = ApiClientConfig()

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}