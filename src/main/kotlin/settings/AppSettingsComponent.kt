package settings

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AppSettingsComponent {
    val panel: JPanel
    private val apiClientIdTextField = JBTextField()
    private val apiSecretTextField = JBTextField()

    val preferredFocusedComponent: JComponent
        get() = apiClientIdTextField

    var apiClientConfig: AppSettingsState.ApiClientConfig
        get() = AppSettingsState.ApiClientConfig(
            id = apiClientIdTextField.text,
            secret = apiSecretTextField.text
        )
        set(newValue) {
            apiClientIdTextField.text = newValue.id
            apiSecretTextField.text = newValue.secret
        }

    init {
        // TODO: Add titled border
        panel = FormBuilder.createFormBuilder()
            .addComponent(
                JBLabel("Papago API settings: "),
                0
            )
            .addLabeledComponent(
                JBLabel("API client ID: "),
                apiClientIdTextField,
                1,
                false
            )
            .addLabeledComponent(
                JBLabel("API client secret: "),
                apiSecretTextField,
                1,
                false
            )
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}
