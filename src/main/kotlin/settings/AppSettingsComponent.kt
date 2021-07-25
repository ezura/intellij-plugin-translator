package settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel

class AppSettingsComponent {
    val panel: JPanel
    private val apiClientTextField = JBTextField()
    private val apiSecretTextField = JBTextField()

    val preferredFocusedComponent: JComponent
        get() = apiClientTextField

    var apiClientText: String
        get() = apiClientTextField.text
        set(newText) {
            apiClientTextField.text = newText
        }

    var apiSecretText: String
        get() = apiSecretTextField.text
        set(newText) {
            apiSecretTextField.text = newText
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
                apiClientTextField,
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
