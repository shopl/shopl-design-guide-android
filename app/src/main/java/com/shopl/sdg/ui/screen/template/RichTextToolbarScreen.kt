package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalDensity
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInput
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputField
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputStyle
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbar
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.AlignCenter
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.AlignLeft
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.AlignRight
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.BulletList
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.Heading1
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.Heading2
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.Heading3
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.Justify
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.Link
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.NumberList
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.Redo
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction.Undo
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarBackgroundColor
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarButtonState
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.base.SDGSampleBaseTab
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentMap

private val backgrounds = persistentListOf(
    SDGSampleBaseTabItem("Light Gray", SDGRichTextToolbarBackgroundColor.LightGray),
    SDGSampleBaseTabItem("White", SDGRichTextToolbarBackgroundColor.White),
    SDGSampleBaseTabItem("Transparent", SDGRichTextToolbarBackgroundColor.Transparent),
)
private val dividers = persistentListOf(SDGSampleBaseTabItem("On", true), SDGSampleBaseTabItem("Off", false))

private data class SampleFormat(val selected: Set<SDGRichTextToolbarAction> = emptySet(), val url: String = "") {
    fun buttons(canUndo: Boolean, canRedo: Boolean) = SDGRichTextToolbarAction.entries.associateWith { action ->
        when {
            (action == Undo && !canUndo) || (action == Redo && !canRedo) -> SDGRichTextToolbarButtonState.Disabled
            action in selected || (action == Link && url.isNotEmpty()) -> SDGRichTextToolbarButtonState.Selected
            else -> SDGRichTextToolbarButtonState.Default
        }
    }.toPersistentMap()

    fun toggle(action: SDGRichTextToolbarAction): SampleFormat {
        val mutuallyExclusive = when (action) {
            Heading1, Heading2, Heading3 -> setOf(Heading1, Heading2, Heading3)
            BulletList, NumberList -> setOf(BulletList, NumberList)
            AlignLeft, AlignCenter, AlignRight, Justify -> setOf(AlignLeft, AlignCenter, AlignRight, Justify)
            else -> emptySet()
        }
        return copy(selected = if (action in selected) selected - action else selected - mutuallyExclusive + action)
    }
}

/** 입력란으로 키보드를 열고 툴바의 선택 상태, 실행 이력, URL 확인과 취소를 확인하는 독립 샘플입니다. */
@Composable
internal fun RichTextToolbarScreen(onClickBack: () -> Unit, onClickMenu: () -> Unit) {
    var background by rememberSaveable { mutableIntStateOf(0) }
    var divider by rememberSaveable { mutableIntStateOf(0) }
    var input by rememberSaveable { mutableStateOf("") }
    var linkOpen by remember { mutableStateOf(false) }
    val history = remember { mutableStateListOf(SampleFormat()) }
    var cursor by remember { mutableIntStateOf(0) }
    val format = history[cursor]
    val inputFocus = remember { FocusRequester() }
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    fun record(next: SampleFormat) {
        if (next == history[cursor]) return
        history.removeRange(cursor + 1, history.size)
        history.add(next)
        cursor = history.lastIndex
    }

    LaunchedEffect(imeVisible) {
        if (!imeVisible) linkOpen = false
    }
    Column(modifier = Modifier.fillMaxSize().imePadding()) {
        Box(modifier = Modifier.weight(1f)) {
            SDGSampleBaseScaffold(
                name = TemplateScene.RichTextToolbar.displayLabel,
                description = "Rich Text 입력 화면의 키보드 위에 배치하여 사용하는, 서식 버튼을 가로로 나열한 툴바 형태의 컴포넌트",
                onClickBack = onClickBack,
                onClickMenu = onClickMenu,
                bodyContent = {
                    Column(
                        modifier = Modifier.padding(SDGSpacing.Spacing16),
                        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16),
                    ) {
                        SDGSampleBaseTab("Background Color", backgrounds, background, { background = it })
                        SDGSampleBaseTab("Show Top Divider", dividers, divider, { divider = it })
                        SDGSimpleTextInput(
                            text = input,
                            placeholder = "눌러서 키보드와 툴바 열기",
                            state = if (input.isEmpty()) SDGSimpleTextInputState.Default
                            else SDGSimpleTextInputState.Completed,
                            inputField = SDGSimpleTextInputField.White,
                            style = SDGSimpleTextInputStyle.Outlined,
                            onTextChange = { input = it },
                            focusRequester = inputFocus,
                        )
                    }
                },
            )
        }
        if (imeVisible) {
            SDGRichTextToolbar(
                buttons = format.buttons(canUndo = cursor > 0, canRedo = cursor < history.lastIndex),
                backgroundColor = backgrounds[background].item,
                showTopDivider = dividers[divider].item,
                showLinkInput = linkOpen,
                initialLinkUrl = format.url,
                onActionClick = { action ->
                    when (action) {
                        Undo -> cursor--
                        Redo -> cursor++
                        Link -> {
                            if (linkOpen) inputFocus.requestFocus()
                            linkOpen = !linkOpen
                        }
                        else -> record(format.toggle(action))
                    }
                },
                onApplyLink = { url -> record(format.copy(url = url)) },
                onDismissLinkInput = {
                    inputFocus.requestFocus()
                    linkOpen = false
                },
            )
        }
    }
}
