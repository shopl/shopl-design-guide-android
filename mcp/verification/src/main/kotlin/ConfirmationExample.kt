package com.shopl.sdg.mcp.examples

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg_common.foundation.SDGColor

/**
 * SDG - [MCP 예제] 실행 확인 Popup
 *
 * SDGCenterPopup 자체가 Dialog를 생성합니다. 확인 후 닫는 시점은 호출 화면에서 결정합니다.
 */
@Composable
fun ConfirmationExample(
    visible: Boolean,
    title: String,
    cancelLabel: String,
    confirmLabel: String,
    confirmEnabled: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    body: @Composable ColumnScope.() -> Unit,
) {
    if (visible) {
        SDGCenterPopup(
            title = title,
            buttonOption = SDGCenterPopupButtonOption.TwoOption(
                cancelLabel = cancelLabel,
                confirmLabel = confirmLabel,
                onClickCancel = onDismiss,
                onClickConfirm = onConfirm,
                onDismiss = onDismiss,
                cancelLabelColor = SDGColor.Neutral700,
                confirmLabelColor = SDGColor.Primary300,
                confirmEnabled = confirmEnabled,
            ),
            body = body,
        )
    }
}
