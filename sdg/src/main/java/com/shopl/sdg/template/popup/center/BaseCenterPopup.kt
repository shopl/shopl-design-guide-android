package com.shopl.sdg.template.popup.center

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg.template.popup.SDGPopupBottomButton
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * Center Popup의 틀이 되는 Base Popup
 */
@Composable
private fun BaseCenterPopup(
    singleButton: Boolean,
    onClickConfirm: () -> Unit,
    isConfirmEnable: Boolean = true,
    title: String? = null,
    titleAlignment: TextAlign = TextAlign.Left,
    confirmLabelColor: Color = SDGColor.Neutral700,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onDismissRequest: (() -> Unit)? = null,
    onClickCancel: (() -> Unit)? = null,
    body: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest?.invoke() ?: run {
                if (!singleButton) {
                    onClickCancel?.invoke()
                } else {
                    onClickConfirm.invoke()
                }
            }
        },
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.4f)

        Column(
            modifier = Modifier
                .padding(horizontal = SDGSpacing.Spacing20)
                .background(
                    color = SDGColor.Neutral0,
                    shape = RoundedCornerShape(SDGCornerRadius.Radius20)
                )
                .padding(top = SDGSpacing.Spacing32)
        ) {

            if (!title.isNullOrBlank()) {
                SDGCenterPopupTitle(
                    modifier = Modifier.padding(horizontal = SDGSpacing.Spacing20),
                    title = title,
                    titleAlignment = titleAlignment
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = SDGSpacing.Spacing20)
                    .weight(1f, false)
                    .verticalScroll(rememberScrollState())
                    .padding(top = SDGSpacing.Spacing4, bottom = SDGSpacing.Spacing32)
            ) {
                body()
            }

            SDGPopupBottomButton(
                singleButton = singleButton,
                cancelLabel = cancelLabel,
                confirmLabel = confirmLabel,
                onClickCancel = onClickCancel,
                onClickConfirm = onClickConfirm,
                isConfirmEnable = isConfirmEnable,
                confirmLabelColor = confirmLabelColor
            )
        }
    }
}

@Composable
private fun SDGCenterPopupTitle(
    modifier: Modifier = Modifier,
    title: String,
    titleAlignment: TextAlign
) {
    SDGText(
        modifier = modifier.fillMaxWidth(),
        text = title,
        typography = SDGTypography.Title2SB,
        textColor = SDGColor.Neutral700,
        textAlign = titleAlignment
    )
}

@Preview
@Composable
private fun PreviewSDGCenterPopup() {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            BaseCenterPopup(
                title = "타이틀",
                singleButton = false,
                onClickConfirm = {},
                body = {
                    Row {
                        SDGText(
                            modifier = Modifier.weight(1f),
                            text = "좌",
                            typography = SDGTypography.Body1R,
                            textColor = SDGColor.Neutral900
                        )
                        SDGText(
                            modifier = Modifier.weight(1f),
                            text = "우",
                            typography = SDGTypography.Body1R,
                            textColor = SDGColor.Neutral900
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSDGCenterPopup_Scrollable() {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            BaseCenterPopup(
                title = "타이틀",
                singleButton = false,
                onClickConfirm = {},
                body = {
                    (1..40).forEach { number ->
                        SDGText(
                            text = "스크롤 샘플 텍스트 $number",
                            typography = SDGTypography.Body1R,
                            textColor = SDGColor.Neutral900
                        )
                    }
                }
            )
        }
    }
}
