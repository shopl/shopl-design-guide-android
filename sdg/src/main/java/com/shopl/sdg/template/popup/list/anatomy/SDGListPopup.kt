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
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg.template.util.list_popup_item.SDGListPopupItem
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed class SDGListPopupItemUiState(
    open val title: String,
    val textColor: Color,
) {
    companion object {
        fun create(title: String, shouldSelect: Boolean): SDGListPopupItemUiState {
            return if (shouldSelect) {
                Selected(title)
            } else {
                Default(title)
            }
        }
    }

    data class Default(override val title: String) : SDGListPopupItemUiState(title, SDGColor.Neutral700)
    data class Selected(override val title: String) : SDGListPopupItemUiState(title, SDGColor.Primary300)
    data class Delete(override val title: String) : SDGListPopupItemUiState(title, SDGColor.Red300)

}

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
        Column(
            modifier = Modifier
                .heightIn(max = (LocalConfiguration.current.screenHeightDp - 120).dp)
                .widthIn(max = (LocalConfiguration.current.screenWidthDp - 40).dp)
                .clip(RoundedCornerShape(20.dp))
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
