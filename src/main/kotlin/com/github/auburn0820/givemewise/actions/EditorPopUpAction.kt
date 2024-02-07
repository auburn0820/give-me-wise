package com.github.auburn0820.givemewise.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory

class EditorPopUpAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val selectionModel = editor.selectionModel

        if (!selectionModel.hasSelection()) return

        val selectedText = selectionModel.selectedText ?: return
        val translationOptions = getTranslationOptions(selectedText)

        showOptionsInPopup(translationOptions, editor)
    }

    private fun getTranslationOptions(text: String): List<String> {
        // TODO: 코드 번역 로직 추가하기
        return listOf("$text in English 1", "$text in English 2", "$text in English 3")
    }

    private fun showOptionsInPopup(options: List<String>, editor: Editor) {
        JBPopupFactory.getInstance().createPopupChooserBuilder(options)
                .setTitle("Select Translation")
                .setItemChosenCallback { selectedOption ->
                    replaceSelectedTextWithTranslation(editor, selectedOption)
                }
                .createPopup()
                .showInBestPositionFor(editor)
    }

    private fun replaceSelectedTextWithTranslation(editor: Editor, translation: String) {
        val document = editor.document
        val selectionModel = editor.selectionModel

        WriteCommandAction.runWriteCommandAction(editor.project) {
            document.replaceString(selectionModel.selectionStart, selectionModel.selectionEnd, translation)
        }
    }
}