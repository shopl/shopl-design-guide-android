package com.shopl.sdg.template.popup.list.anatomy

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.template.util.list_popup_item_ui_state.SDGListPopupItemUiState
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Popup - Anatomy - Body
 *
 * Height는 콘텐츠에 따라 가변사이즈입니다.
 * Text와 Divider로 구성됩니다.
 * Scroll 영역은 전체입니다.
 * *마지막 리스트의 Diver는 생략합니다.
 * *마지막 리스트 하단에는 10px 패딩이 추가됩니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?m=auto&node-id=11228-13323&t=LlVpB7SDwYTIxUWn-1">Figma</a>
 */

@Composable
fun SDGListPopupBody(
    uiState: SDGListPopupItemUiState,
    onClick: (SDGListPopupItemUiState) -> Unit,
) {
    SDGText(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(true, rippleColor = SDGColor.Neutral350) { onClick(uiState) }
            .padding(vertical = SDGSpacing.Spacing14, horizontal = SDGSpacing.Spacing20),
        text = uiState.title,
        textColor = uiState.textColor,
        typography = SDGTypography.Body1R,
        textAlign = TextAlign.Center,
    )
}


@Preview
@Composable
fun PreviewSDGListPopupItem() {
    val item = SDGListPopupItemUiState.Default(
        "Default Text가 길어지면 상하 padding 유지한 상태로 줄바꿈하여 전체 노출"
    )
    SDGListPopupBody(
        uiState = item,
        onClick = {}
    )
}