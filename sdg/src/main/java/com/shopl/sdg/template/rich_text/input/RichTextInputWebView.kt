package com.shopl.sdg.template.rich_text.input

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.shopl.sdg.template.rich_text.common.RichTextWebView
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarButtonState
import kotlinx.collections.immutable.toPersistentMap
import org.json.JSONObject

internal class RichTextInputWebView(context: Context) : RichTextWebView(context) {
    override val openLinksInBrowser = false
    private var inputState: SDGRichTextInputState? = null
    private var enabledForEditing = false
    private var hint = ""
    private var initialized = false

    fun bind(state: SDGRichTextInputState, enabled: Boolean, placeholder: String) {
        if (inputState === state && enabledForEditing == enabled && hint == placeholder) return
        inputState = state
        state.editor = this
        if (enabledForEditing && !enabled) state.clearFocus()
        enabledForEditing = enabled
        hint = placeholder
        isFocusableInTouchMode = enabled
        isFocusable = enabled
        if (ready) displayContent()
    }

    override fun displayContent() {
        val state = inputState ?: return
        val update = JSONObject().put("input", true).put("enabled", enabledForEditing).put("placeholder", hint)
        if (!initialized) update.put("html", state.html)
        initialized = true
        evaluateJavascript("window.sdgRichText.update(${JSONObject.quote(update.toString())})", null)
    }

    // JavaBridge 스레드에서 메시지를 읽고 Compose 상태 변경만 UI 스레드로 전달합니다.
    override fun onEditorMessage(type: String, message: JSONObject) {
        when (type) {
            "content" -> {
                val html = message.getString("html")
                val count = message.getInt("count")
                post {
                    if (!released) inputState?.apply {
                        this.html = html
                        characterCount = count
                    }
                }
            }
            "toolbar" -> {
                val focused = message.getBoolean("focused")
                val url = message.getString("url")
                val selected = message.getJSONArray("selected")
                val names = (0 until selected.length()).map { selected.getString(it) }.toSet()
                val states = SDGRichTextToolbarAction.entries.associateWith { action ->
                    when {
                        action == SDGRichTextToolbarAction.Undo && !message.getBoolean("canUndo") ||
                            action == SDGRichTextToolbarAction.Redo && !message.getBoolean("canRedo") ->
                            SDGRichTextToolbarButtonState.Disabled
                        action.name in names -> SDGRichTextToolbarButtonState.Selected
                        else -> SDGRichTextToolbarButtonState.Default
                    }
                }.toPersistentMap()
                post { if (!released) inputState?.updateToolbar(focused, url, states) }
            }
        }
    }

    fun action(name: String, value: String? = null) {
        if (!ready || released || !enabledForEditing) return
        val argument = value?.let(JSONObject::quote) ?: "null"
        evaluateJavascript("window.sdgRichText.action(${JSONObject.quote(name)}, $argument)", null)
    }

    fun restoreSelection(url: String?) {
        requestFocus()
        action("FinishLink", url)
        context.getSystemService(InputMethodManager::class.java)?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}
