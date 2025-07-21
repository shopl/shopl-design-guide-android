package com.shopl.sdg.template.popup.list.anatomy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg.template.util.list_popup_item.SDGListPopupItem
import com.shopl.sdg.template.util.list_popup_item_ui_state.SDGListPopupItemUiState
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SDGListPopup(
    items: PersistentList<SDGListPopupItemUiState>,
    onSelected: (SDGListPopupItemUiState) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.4f)

        val windowSizePx = LocalWindowInfo.current.containerSize
        val density = LocalDensity.current
        val maxHeightDp = with(density) { windowSizePx.height.toDp() } - 120.dp
        val maxWidthDp = with(density) { windowSizePx.width.toDp() } - 40.dp

        Column(
            modifier = Modifier
                .heightIn(max = maxHeightDp)
                .widthIn(max = maxWidthDp)
                .clip(RoundedCornerShape(SDGCornerRadius.Radius20))
                .background(SDGColor.Neutral0)
                .verticalScroll(rememberScrollState())
        ) {
            items.forEachIndexed { index, item ->
                SDGListPopupItem(uiState = item) {
                    onSelected(item)
                    onDismissRequest()
                }
                if (index != items.lastIndex) {
                    HorizontalDivider(color = SDGColor.Neutral200)
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewSDGListPopup() {
    val popupItems = persistentListOf(
        SDGListPopupItemUiState.Default("Default"),
        SDGListPopupItemUiState.Selected("Selected"),
        SDGListPopupItemUiState.Delete("Delete")
    )
    SDGListPopup(
        items = popupItems,
        onSelected = {},
        onDismissRequest = {}
    )
}
