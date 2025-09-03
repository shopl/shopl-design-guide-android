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
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

@Composable
fun SDGListPopupBody(
    uiState: SDGListPopupItemUiState,
    onClick: (SDGListPopupItemUiState) -> Unit,
) {
    SDGText(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(true, rippleColor = SDGColor.Neutral350) { onClick(uiState) }
            .padding(vertical = Spacing16, horizontal = Spacing8),
        text = uiState.title,
        textColor = uiState.textColor,
        typography = SDGTypography.Body1R,
        textAlign = TextAlign.Center,
    )
}


@Preview(showBackground = true)
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