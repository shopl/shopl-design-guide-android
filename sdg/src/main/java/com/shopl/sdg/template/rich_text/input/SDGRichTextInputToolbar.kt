package com.shopl.sdg.template.rich_text.input

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbar

/**
 * [state]의 선택 영역에 서식을 적용합니다. 입력창과 같은 상태를 전달하고, 키보드 인셋을 적용한 화면 하단에 배치합니다.
 * 본문이나 링크 입력창을 편집하는 동안에만 표시되며, 키보드를 닫으면 편집 포커스도 해제됩니다.
 */
@Composable
fun SDGRichTextInputToolbar(state: SDGRichTextInputState) {
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    var wasVisible by remember(state) { mutableStateOf(false) }
    LaunchedEffect(state, imeVisible) {
        if (wasVisible && !imeVisible && (state.isFocused || state.linkOpen)) state.clearFocus()
        wasVisible = imeVisible
    }
    if (imeVisible && (state.isFocused || state.linkOpen)) {
        SDGRichTextToolbar(
            buttons = state.buttons,
            showLinkInput = state.linkOpen,
            initialLinkUrl = state.linkUrl,
            onActionClick = state::selectAction,
            onApplyLink = state::finishLink,
            onDismissLinkInput = { state.finishLink() },
        )
    }
}
