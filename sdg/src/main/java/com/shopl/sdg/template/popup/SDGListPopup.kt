package com.shopl.sdg.template.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText

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
    items: List<SDGListPopupItemUiState>,
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

@Composable
fun SDGListPopupItem(
    uiState: SDGListPopupItemUiState,
    onClick: (SDGListPopupItemUiState) -> Unit,
) {
    IOText(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(true, rippleColor = SDGColor.Neutral350) { onClick(uiState) }
            .padding(vertical = 14.dp, horizontal = 20.dp),
        text = uiState.title,
        textColor = uiState.textColor,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
    )
}