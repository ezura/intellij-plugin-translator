package settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AppSettingsComponent {
    val panel: JPanel
    private val apiClientIdTextField = JBTextField()
    private val apiSecretTextField = JBTextField()

    // TODO: Split component into other class.
    private val targetLanguageTextField = JBTextField()
    private val sourceLanguageTextField = JBTextField()
    private val isEnabledLanguageDetectionCheckBox = JBCheckBox()

    val preferredFocusedComponent: JComponent
        get() = apiClientIdTextField

    var apiClientSettings: AppSettingsState.ApiClientSettings
        get() = AppSettingsState.ApiClientSettings(
            id = apiClientIdTextField.text,
            secret = apiSecretTextField.text
        )
        set(newValue) {
            apiClientIdTextField.text = newValue.id
            apiSecretTextField.text = newValue.secret
        }

    var languageSettings: AppSettingsState.LanguageSettings
        get() = AppSettingsState.LanguageSettings(
            targetLanguage = targetLanguageTextField.text,
            defaultSourceLanguage = sourceLanguageTextField.text,
            isEnabledLanguageDetection = isEnabledLanguageDetectionCheckBox.isSelected
        )
        set(newValue) {
            targetLanguageTextField.text = newValue.targetLanguage
            sourceLanguageTextField.text = newValue.defaultSourceLanguage
            isEnabledLanguageDetectionCheckBox.isSelected = newValue.isEnabledLanguageDetection
        }

    init {
        panel = FormBuilder.createFormBuilder()
            // TODO: Add titled border
            // TODO: Split component
            .addComponent(
                JBLabel("Papago API"),
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
            .addSeparator()
            // TODO: Add titled border
            // TODO: Split component
            .addComponent(
                JBLabel("Language"),
                0
            )
            .addLabeledComponent(
                JBLabel("Target language: "),
                targetLanguageTextField,
                1,
                false
            )
            .addLabeledComponent(
                JBLabel("Source language: "),
                sourceLanguageTextField,
                1,
                false
            )
            .addLabeledComponent(
                JBLabel("Enable languageDetection(not implemented yet): "),
                isEnabledLanguageDetectionCheckBox,
                1,
                false
            )
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}
