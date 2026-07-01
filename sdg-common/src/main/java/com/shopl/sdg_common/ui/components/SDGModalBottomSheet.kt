package com.shopl.sdg_common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.util.SDGPopupPreviewContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    containerColor: Color = SDGColor.Neutral0,
    contentColor: Color = SDGColor.Neutral0,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    if (LocalInspectionMode.current) {
        SDGModalBottomSheetInspectionPreview(
            modifier = modifier,
            containerColor = containerColor,
            content = content,
        )
        return
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = SDGCornerRadius.Radius20,
            topEnd = SDGCornerRadius.Radius20
        ),
        containerColor = containerColor,
        contentColor = contentColor,
        scrimColor = SDGColor.Neutral900_a40,
        dragHandle = null,
    ) {
        SDGModalBottomSheetContent(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            content = content,
        )
    }
}

@Composable
private fun SDGModalBottomSheetContent(
    modifier: Modifier,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    Column(modifier = modifier) {
        content()
    }
}

/**
 * Compose Preview에서 ModalBottomSheet 대신 Bottom Sheet 콘텐츠를 인라인으로 렌더링합니다.
 */
@Composable
private fun SDGModalBottomSheetInspectionPreview(
    modifier: Modifier,
    containerColor: Color,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    SDGPopupPreviewContainer(contentAlignment = Alignment.BottomCenter) {
        SDGModalBottomSheetContent(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(
                    color = containerColor,
                    shape = RoundedCornerShape(
                        topStart = SDGCornerRadius.Radius20,
                        topEnd = SDGCornerRadius.Radius20
                    )
                ),
            content = content,
        )
    }
}
