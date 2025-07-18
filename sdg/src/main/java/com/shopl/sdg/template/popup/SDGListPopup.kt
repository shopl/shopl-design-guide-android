package com.shopl.sdg.template.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGListBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    ),
    items: PersistentList<SDGListPopupItemUiState>,
    onSelected: (SDGListPopupItemUiState) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,

        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        ),
        containerColor = SDGColor.Neutral0,
        dragHandle = null,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            SDGSelectableTextList(
                items = items,
                onSelected = onSelected,
                onDismissRequest = onDismissRequest
            )
        }
    }
}


@Composable
private fun SDGSelectableTextList(
    modifier: Modifier = Modifier,
    listContentPadding: PaddingValues = PaddingValues(bottom = 10.dp),
    items: PersistentList<SDGListPopupItemUiState>,
    onSelected: (SDGListPopupItemUiState) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = listContentPadding
    ) {
        itemsIndexed(items) { index, item ->
            SDGListPopupItem(uiState = item) {
                onSelected(item)
                onDismissRequest()
            }

            if (index != items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    color = SDGColor.Neutral200
                )
            }
        }
    }
}


@Composable
private fun SDGListPopupItem(
    uiState: SDGListPopupItemUiState,
    onClick: (SDGListPopupItemUiState) -> Unit,
) {
    SDGText(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(true, rippleColor = SDGColor.Neutral350) { onClick(uiState) }
            .padding(vertical = 14.dp, horizontal = 20.dp),
        text = uiState.title,
        textColor = uiState.textColor,
        typography = SDGTypography.Body1R,
        textAlign = TextAlign.Center,
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewSDGListBottomSheet() {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val popupItems = persistentListOf(
        SDGListPopupItemUiState.Default("Default Text가 길어지면 상하 padding 유지한 상태로 줄바꿈하여 전체 노출 "),
        SDGListPopupItemUiState.Selected("Selected"),
        SDGListPopupItemUiState.Delete("Delete")
    )
    LaunchedEffect(Unit) { sheetState.expand() }
    SDGListBottomSheet(
        sheetState = sheetState,
        items = popupItems,
        onSelected = {},
        onDismissRequest = {}
    )
}