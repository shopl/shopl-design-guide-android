package com.shopl.sdg.template.rich_text.input

import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbar

/** 본문과 같은 창에 툴바를 배치해 스크롤 위치에 관계없이 키보드 바로 위에서 선택 영역을 편집합니다. */
@Composable
internal fun SDGRichTextInputToolbar(state: SDGRichTextInputState) {
    if (!state.isFocused && !state.linkOpen) return

    val ime = WindowInsets.ime
    val density = LocalDensity.current
    val imeVisible = ime.getBottom(density) > 0
    var wasVisible by remember(state) { mutableStateOf(false) }
    LaunchedEffect(state, imeVisible) {
        if (wasVisible && !imeVisible) state.clearFocus()
        wasVisible = imeVisible
    }
    if (!imeVisible) return

    val root = LocalView.current.rootView
    val host = root.findViewById<FrameLayout>(android.R.id.content) ?: return
    val composition = rememberCompositionContext()
    DisposableEffect(host, state, composition, ime, density) {
        val toolbar = ComposeView(host.context).apply {
            setParentCompositionContext(composition)
            setContent {
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
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM,
        )
        host.addView(toolbar, layoutParams)

        // 창이 이미 키보드만큼 줄어든 경우에는 여백을 중복 적용하지 않습니다.
        val location = IntArray(2)
        val position = ViewTreeObserver.OnPreDrawListener {
            host.getLocationInWindow(location)
            val imeTop = root.height - ime.getBottom(density)
            toolbar.translationY = (imeTop - location[1] - toolbar.bottom).coerceAtMost(0).toFloat()
            true
        }
        val focus = ViewTreeObserver.OnGlobalFocusChangeListener { _, next ->
            if (next != null && !toolbar.hasFocus() && state.editor?.hasFocus() != true) {
                state.clearFocus()
            }
        }
        host.viewTreeObserver.addOnPreDrawListener(position)
        host.viewTreeObserver.addOnGlobalFocusChangeListener(focus)
        onDispose {
            host.viewTreeObserver.removeOnGlobalFocusChangeListener(focus)
            host.viewTreeObserver.removeOnPreDrawListener(position)
            host.removeView(toolbar)
            toolbar.disposeComposition()
        }
    }
}
