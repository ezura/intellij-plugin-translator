import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TranslationAction: AnAction() {

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabledAndVisible = !e.presentation.text.isNullOrEmpty()
    }

    override fun actionPerformed(e: AnActionEvent) {
        TODO("Not yet implemented")
    }
}