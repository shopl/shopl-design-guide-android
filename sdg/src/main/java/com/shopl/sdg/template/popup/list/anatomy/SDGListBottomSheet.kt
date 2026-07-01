package com.shopl.sdg.template.popup.list.anatomy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.util.list_popup_item_ui_state.SDGListPopupItemUiState
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.util.SDGPopupPreviewContainer
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG - Popup - Anatomy - List Bottom Sheet
 *
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?m=auto&node-id=11228-13323">Figma</a>
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGListBottomSheet(
    items: PersistentList<SDGListPopupItemUiState>,
    onSelected: (SDGListPopupItemUiState) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
) {
    if (LocalInspectionMode.current) {
        SDGListBottomSheetInspectionPreview(
            items = items,
            onSelected = onSelected,
            onDismissRequest = onDismissRequest,
        )
        return
    }

    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val maxHeight = with(density) {
        (windowInfo.containerSize.height * 0.7f).toDp()
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = SDGCornerRadius.Radius20,
            topEnd = SDGCornerRadius.Radius20
        ),
        containerColor = SDGColor.Neutral0,
        contentColor = SDGColor.Neutral0,
        scrimColor = SDGColor.Neutral900_a40,
        dragHandle = null,
        modifier = Modifier.fillMaxWidth()
    ) {
        SDGListBottomSheetContent(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight)
                .navigationBarsPadding(),
            items = items,
            onSelected = onSelected,
            onDismissRequest = onDismissRequest,
        )
    }
}

@Composable
private fun SDGListBottomSheetContent(
    modifier: Modifier,
    items: PersistentList<SDGListPopupItemUiState>,
    onSelected: (SDGListPopupItemUiState) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Column(modifier = modifier) {
        SDGSelectableTextList(
            items = items,
            onSelected = onSelected,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun SDGSelectableTextList(
    modifier: Modifier = Modifier,
    listContentPadding: PaddingValues = PaddingValues(bottom = SDGSpacing.Spacing10),
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
            SDGListPopupBody(uiState = item) {
                onSelected(item)
                onDismissRequest()
            }

            if (index != items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SDGSpacing.Spacing12),
                    color = SDGColor.Neutral200
                )
            }
        }
    }
}

/**
 * Compose Preview에서 ModalBottomSheet 대신 List Bottom Sheet 콘텐츠를 인라인으로 렌더링합니다.
 */
@Composable
private fun SDGListBottomSheetInspectionPreview(
    items: PersistentList<SDGListPopupItemUiState>,
    onSelected: (SDGListPopupItemUiState) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val previewMaxHeight = with(density) {
        if (windowInfo.containerSize.height == 0) {
            448.dp
        } else {
            (windowInfo.containerSize.height * 0.7f).toDp()
        }
    }

    SDGPopupPreviewContainer(contentAlignment = Alignment.BottomCenter) {
        SDGListBottomSheetContent(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = previewMaxHeight)
                .navigationBarsPadding()
                .background(
                    color = SDGColor.Neutral0,
                    shape = RoundedCornerShape(
                        topStart = SDGCornerRadius.Radius20,
                        topEnd = SDGCornerRadius.Radius20
                    )
                ),
            items = items,
            onSelected = onSelected,
            onDismissRequest = onDismissRequest,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewSDGListBottomSheet() {
    val density = LocalDensity.current
    val sheetState = remember {
        SheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { true },
            initialValue = SheetValue.Expanded,
            density = density,
            skipHiddenState = false
        )
    }
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
