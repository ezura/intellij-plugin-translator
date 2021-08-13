import com.intellij.ide.BrowserUtil
import com.intellij.notification.NotificationListener
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import kotlinx.coroutines.*
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener

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
        val selectedText = caret.selectedText
        val translationTarget = (selectedText ?: "")
            // For multiline comments, remove "//", "///" at line start.
            // "/* ... */" doesn't interfere with the translation, so only remove linebreaks.
            .replace(Regex("(^|\n) *///?"), "")
            .replace("\n", " ")

        runBlocking {
            // TODO: execute on background
            val translationResult = runCatching {
                Translator().translateAsync(translationTarget).await()
            }

            if (selectedText != caret.selectedText) return@runBlocking

            val actualPosition = editor.visualPositionToXY(visualPosition)
                .also {
                    val editorContentPosition = editor.contentComponent.locationOnScreen
                    it.translate(
                        editorContentPosition.x,
                        editorContentPosition.y + editor.lineHeight
                    )
                }

            JBPopupFactory.getInstance()
                .let { factory ->
                    val listener = HyperlinkListener { event ->
                        if (event.eventType == HyperlinkEvent.EventType.ACTIVATED
                            && listOf("http", "https").contains(event.url.protocol)) {
                            BrowserUtil.browse(event.url)
                        }
                    }
                    translationResult.fold(
                        { factory.createHtmlTextBalloonBuilder(it, MessageType.INFO, listener) },
                        { factory.createHtmlTextBalloonBuilder(it.localizedMessage, MessageType.ERROR, listener) }
                    )
                }
                .createBalloon()
                .show(
                    RelativePoint.fromScreen(actualPosition),
                    Balloon.Position.below
                )
        }
    }
}
