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
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R

/**
 * SDG - Template - Popup - Bottom Popup
 *
 * 태스크 수행을 위한 콘텐츠를 포함하는 화면 하단에 노출되는 팝업
 *
 * @param inputState SimpeInput 에서 사용되는 state, Error인 경우 현재 message 별도 출력하지 않음
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19210-2895&m=dev">Figma</a>
 */
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