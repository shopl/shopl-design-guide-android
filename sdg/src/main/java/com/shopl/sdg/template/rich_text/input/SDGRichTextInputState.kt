package com.shopl.sdg.template.rich_text.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarButtonState
import kotlinx.collections.immutable.PersistentMap

/** 한 입력창의 HTML과 글자 수, 현재 선택 영역의 서식을 보관합니다. */
@Stable
class SDGRichTextInputState(initialHtml: String = "") {
    var html by mutableStateOf(initialHtml)
        internal set
    var characterCount by mutableIntStateOf(0)
        internal set
    var isFocused by mutableStateOf(false)
        internal set
    internal var buttons by mutableStateOf(SDGRichTextToolbarAction.defaultButtons)
        private set
    internal var linkOpen by mutableStateOf(false)
        private set
    internal var linkUrl by mutableStateOf("")
        private set
    internal var editor: RichTextInputWebView? = null

    internal fun updateToolbar(
        focused: Boolean,
        url: String,
        states: PersistentMap<SDGRichTextToolbarAction, SDGRichTextToolbarButtonState>,
    ) {
        isFocused = focused
        if (!linkOpen) linkUrl = url
        buttons = states
    }

    internal fun selectAction(action: SDGRichTextToolbarAction) {
        if (action == SDGRichTextToolbarAction.Link) {
            if (linkOpen) finishLink() else {
                editor?.action("SaveLink")
                linkOpen = true
            }
        } else {
            if (linkOpen) finishLink()
            editor?.action(action.name)
        }
    }

    internal fun finishLink(url: String? = null) {
        if (!linkOpen) return
        linkOpen = false
        editor?.restoreSelection(url)
    }

    internal fun clearFocus() {
        linkOpen = false
        editor?.action("Blur")
        editor?.clearFocus()
    }

    internal fun detach(view: RichTextInputWebView) {
        if (editor !== view) return
        editor = null
        isFocused = false
        linkOpen = false
        buttons = SDGRichTextToolbarAction.defaultButtons
    }
}

/** 처음 표시할 [initialHtml]로 입력 상태를 만듭니다. 작성 결과는 [SDGRichTextInputState.html]에서 읽습니다. */
@Composable
fun rememberSDGRichTextInputState(initialHtml: String = ""): SDGRichTextInputState =
    remember { SDGRichTextInputState(initialHtml) }
