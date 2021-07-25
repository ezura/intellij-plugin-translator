package settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class AppSettingsConfigurable : Configurable {
    private var settingsComponent: AppSettingsComponent? = null

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String  = "Plugin: Translation Plugin"

    override fun getPreferredFocusedComponent(): JComponent =
        settingsComponent!!.preferredFocusedComponent

    override fun createComponent(): JComponent? {
        settingsComponent = AppSettingsComponent()
        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val currentSettings = AppSettingsState.instance
        // TODO: Data class
        return !currentSettings.client.equals(settingsComponent!!.apiClientText) ||
                !currentSettings.secret.equals(settingsComponent!!.apiSecretText)
    }

    override fun apply() {
        val settings: AppSettingsState = AppSettingsState.instance
        settings.client = settingsComponent!!.apiClientText
        settings.secret = settingsComponent!!.apiSecretText
    }

    override fun reset() {
        val settings: AppSettingsState = AppSettingsState.instance
        settingsComponent!!.apiClientText = settings.client
        settingsComponent!!.apiSecretText = settings.secret
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}
