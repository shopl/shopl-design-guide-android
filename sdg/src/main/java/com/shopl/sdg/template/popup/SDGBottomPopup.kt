package com.shopl.sdg.template.popup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGBottomPopup(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    containerColor: Color = SDGColor.Neutral0,
    contentColor: Color = SDGColor.Neutral0,
    singleButton: Boolean,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickCancel: (() -> Unit)? = null,
    onClickConfirm: () -> Unit,
    isConfirmEnable: Boolean = true,
    confirmLabelColor: Color = SDGColor.Neutral700,
    content: @Composable() () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onClickCancel ?: onClickConfirm,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = containerColor,
        contentColor = contentColor,
        scrimColor = SDGColor.Neutral900_a40,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 32.dp,
                        horizontal = 28.dp,
                    ),
            ) {
                content()
            }
            SDGPopupBottomButton(
                singleButton = singleButton,
                cancelLabel = cancelLabel,
                confirmLabel = confirmLabel,
                onClickCancel = onClickCancel,
                onClickConfirm = onClickConfirm,
                isConfirmEnable = isConfirmEnable,
                confirmLabelColor = confirmLabelColor,
                isBottomDialog = true
            )
        }
    }
}