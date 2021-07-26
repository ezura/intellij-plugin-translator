package settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class AppSettingsConfigurable : Configurable {
    private var settingsComponent: AppSettingsComponent? = null

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String  = "Translation Plugin"

    override fun getPreferredFocusedComponent(): JComponent =
        settingsComponent!!.preferredFocusedComponent

    override fun createComponent(): JComponent? {
        settingsComponent = AppSettingsComponent()
        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val currentSettings = AppSettingsState.instance
        return currentSettings.apiClientSettings != settingsComponent!!.apiClientSettings
                || currentSettings.languageSettings != settingsComponent!!.languageSettings
    }

    override fun apply() {
        val settings = AppSettingsState.instance
        settings.apiClientSettings = settingsComponent!!.apiClientSettings
        settings.languageSettings = settingsComponent!!.languageSettings
    }

    override fun reset() {
        val settings = AppSettingsState.instance
        settingsComponent!!.apiClientSettings = settings.apiClientSettings
        settingsComponent!!.languageSettings = settings.languageSettings
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}
