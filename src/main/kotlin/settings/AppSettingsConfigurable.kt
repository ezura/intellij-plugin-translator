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
        val apiClientConfig = settingsComponent!!.apiClientConfig
        return currentSettings.apiClientConfig != apiClientConfig
    }

    override fun apply() {
        val settings = AppSettingsState.instance
        settings.apiClientConfig = settingsComponent!!.apiClientConfig
    }

    override fun reset() {
        val settings = AppSettingsState.instance
        settingsComponent!!.apiClientConfig = settings.apiClientConfig
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}
