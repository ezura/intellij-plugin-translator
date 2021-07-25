import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint

class TranslationAction: AnAction() {

    override fun update(e: AnActionEvent) {
        super.update(e)
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val selectedText = editor.caretModel.primaryCaret.selectedText
        e.presentation.isVisible = true
        e.presentation.isEnabled = !selectedText.isNullOrBlank()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val caret = editor.caretModel.primaryCaret
        val visualPosition = caret.selectionEndPosition
        // TODO: trim line break and "//"
        val selectedText = caret.selectedText ?: ""
        // TODO: Call a translate API
        // TODO: Cancel the action if the selected text has been changed.
        val actualPosition = editor.visualPositionToXY(visualPosition)
            .also {
                val editorContentPosition = editor.contentComponent.locationOnScreen
                it.translate(
                    editorContentPosition.x,
                    editorContentPosition.y + editor.lineHeight
                )
            }

        JBPopupFactory.getInstance()
            .createHtmlTextBalloonBuilder(selectedText, MessageType.INFO, null)
            .createBalloon()
            .show(
                RelativePoint.fromScreen(actualPosition),
                Balloon.Position.below
            )
    }
}
