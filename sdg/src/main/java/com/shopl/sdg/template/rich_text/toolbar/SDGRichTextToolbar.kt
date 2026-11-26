package com.shopl.sdg.template.rich_text.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.rich_text.toolbar.preview.SDGRichTextToolbarPreviewParameter
import com.shopl.sdg.template.rich_text.toolbar.preview.SDGRichTextToolbarPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.PersistentMap

/**
 * Rich Text 입력 화면의 키보드 위에 배치하여 사용하는, 서식 버튼을 가로로 나열한 툴바 형태의 컴포넌트
 *
 * @param buttons 표시할 동작과 현재 선택 영역에서의 버튼 상태. 실행 취소·다시 실행은 왼쪽에 고정됩니다.
 * @param onActionClick 누른 동작. Link를 누르면 호출부에서 편집 위치를 보존하고 [showLinkInput]을 갱신합니다.
 * @param onApplyLink 확인한 URL을 편집기에 적용하는 콜백.
 * @param onDismissLinkInput 확인 또는 닫기를 누르면 호출됩니다. 호출부에서 링크 입력을 닫습니다.
 * @param backgroundColor 툴바 배경. 기본값은 Light Gray입니다.
 * @param showTopDivider 서식 버튼 영역의 상단 구분선 표시 여부.
 * @param showLinkInput 툴바 위에 URL 입력 영역을 표시하고 Link 버튼을 선택 상태로 표시할지 여부.
 * @param initialLinkUrl 링크 입력을 열 때 표시할 기존 URL.
 * @version 2.4.61
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30786-663&m=dev">Figma</a>
 */
@Composable
fun SDGRichTextToolbar(
    onActionClick: (SDGRichTextToolbarAction) -> Unit,
    onApplyLink: (String) -> Unit,
    onDismissLinkInput: () -> Unit,
    buttons: PersistentMap<SDGRichTextToolbarAction, SDGRichTextToolbarButtonState> =
        SDGRichTextToolbarAction.defaultButtons,
    backgroundColor: SDGRichTextToolbarBackgroundColor = SDGRichTextToolbarBackgroundColor.LightGray,
    showTopDivider: Boolean = true,
    showLinkInput: Boolean = false,
    initialLinkUrl: String = "",
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (showLinkInput) {
            var url by rememberSaveable(initialLinkUrl) { mutableStateOf(initialLinkUrl) }
            SDGRichTextLinkInput(
                url = url,
                onUrlChange = { url = it },
                onConfirm = {
                    onApplyLink(url.trim())
                    onDismissLinkInput()
                },
                onDismiss = onDismissLinkInput,
            )
        }
        if (showTopDivider) HorizontalDivider(thickness = 1.dp, color = SDGColor.Neutral200)
        val spacing = Arrangement.spacedBy(SDGSpacing.Spacing4)
        Row(
            modifier = Modifier.fillMaxWidth().height(44.dp).background(backgroundColor.color)
                .padding(horizontal = SDGSpacing.Spacing4, vertical = SDGSpacing.Spacing2),
            horizontalArrangement = spacing,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val actions = SDGRichTextToolbarAction.entries.filter { it in buttons }
            val (history, formats) = actions.partition { it.group == ToolbarGroup.History }
            history.forEach { action ->
                SDGRichTextToolbarButton(action, buttons.getValue(action)) { onActionClick(action) }
            }
            if (history.isNotEmpty() && formats.isNotEmpty()) ToolbarDivider()
            Row(
                modifier = Modifier.weight(1f).horizontalScroll(rememberScrollState()),
                horizontalArrangement = spacing,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                formats.forEachIndexed { index, action ->
                    if (index > 0 && formats[index - 1].group != action.group) ToolbarDivider()
                    val state = buttons.getValue(action)
                    val showSelectedLink = action == SDGRichTextToolbarAction.Link && showLinkInput &&
                        state != SDGRichTextToolbarButtonState.Disabled
                    SDGRichTextToolbarButton(
                        action = action,
                        state = if (showSelectedLink) SDGRichTextToolbarButtonState.Selected else state,
                        onClick = { onActionClick(action) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ToolbarDivider() {
    VerticalDivider(modifier = Modifier.size(1.dp, 20.dp), thickness = 1.dp, color = SDGColor.Neutral200)
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun PreviewSDGRichTextToolbar(
    @PreviewParameter(SDGRichTextToolbarPreviewParameterProvider::class) parameter: SDGRichTextToolbarPreviewParameter,
) {
    SDGRichTextToolbar(
        onActionClick = {},
        onApplyLink = {},
        onDismissLinkInput = {},
        backgroundColor = parameter.backgroundColor,
        buttons = parameter.buttons,
        showLinkInput = parameter.showLinkInput,
        initialLinkUrl = parameter.url,
        showTopDivider = parameter.showTopDivider,
    )
}
