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
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Template - Popup - Bottom Popup
 *
 * 태스크 수행을 위한 콘텐츠를 포함하는 화면 하단에 노출되는 팝업
 *
 * @param sheetState BottomSheetState
 *                   만약 중간 펼침(PartiallyExpanded) 상태가 필요하다면
 *                   rememberModalBottomSheetState(skipPartiallyExpanded = true)으로 설정
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19210-2895&m=dev">Figma</a>
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGBottomPopup(
    singleButton: Boolean,
    onClickConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    containerColor: Color = SDGColor.Neutral0,
    contentColor: Color = SDGColor.Neutral0,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickCancel: (() -> Unit)? = null,
    isConfirmEnable: Boolean = true,
    confirmLabelColor: Color = SDGColor.Neutral700,
) {
    ModalBottomSheet(
        onDismissRequest = onClickCancel ?: onClickConfirm,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = SDGSpacing.Spacing24,
                        top = SDGSpacing.Spacing24,
                        end = SDGSpacing.Spacing24,
                        bottom = SDGSpacing.Spacing28
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewSDGBottomPopup() {
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
    SDGBottomPopup(
        sheetState = sheetState,
        singleButton = false,
        onClickConfirm = {},
        content = {
            SDGText(
                text = "SDGBottomPopup Content",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1R
            )
        }
    )
}