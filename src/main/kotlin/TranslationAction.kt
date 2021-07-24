import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TranslationAction: AnAction() {

    override fun update(e: AnActionEvent) {
        super.update(e)
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val selectedText = editor.caretModel.primaryCaret.selectedText
        e.presentation.isVisible = true
        e.presentation.isEnabled = !selectedText.isNullOrBlank()
    }

    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }
}