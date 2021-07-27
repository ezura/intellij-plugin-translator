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
    data class ApiClientSettings(
        var id: String = "",
        var secret: String = ""
    )

    class ApiClientSettingsConverter : Converter<ApiClientSettings?>() {
        override fun fromString(value: String): ApiClientSettings =
            Json.decodeFromString(value)

        override fun toString(value: ApiClientSettings): String =
            Json.encodeToString(value)
    }

    @Serializable
    data class LanguageSettings(
        var targetLanguage: String = "en",
        // TODO: Move to setting file for each project.
        var defaultSourceLanguage: String = "ko",
        var isEnabledLanguageDetection: Boolean = true
    )

    class LanguageSettingsConverter : Converter<LanguageSettings?>() {
        override fun fromString(value: String): LanguageSettings =
            Json.decodeFromString(value)

        override fun toString(value: LanguageSettings): String =
            Json.encodeToString(value)
    }

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }

    @OptionTag(converter = ApiClientSettingsConverter::class)
    var apiClientSettings = ApiClientSettings()
    @OptionTag(converter = LanguageSettingsConverter::class)
    var languageSettings = LanguageSettings()

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}