package settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "settings.AppSettingsState",
    storages = [Storage("TranslationPluginSettings.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState?> {

//    data class ApiConfig(
//        var client: String = "",
//        var secret: String = ""
//    )
//
//    var appConfig: ApiConfig = ApiConfig()

    var client: String = ""
    var secret: String = ""

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }
}