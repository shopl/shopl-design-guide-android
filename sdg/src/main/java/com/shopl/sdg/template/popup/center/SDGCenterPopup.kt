package com.shopl.sdg.template.popup.center

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Popup - Center Popup
 *
 * 정보 전달 및 컨펌을 위한 콘텐츠를 포함하는 화면 중앙에 노출되는 팝업
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19174-17196&t=dilCeVYD7pIRNnAx-4">Figma</a>
 */
private const val DIALOG_DIM_AMOUNT = 0.4f

@Composable
fun SDGCenterPopup(
    buttonOption: SDGCenterPopupButtonOption,
    title: String? = null,
    titleAlignment: TextAlign = TextAlign.Left,
    body: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = {
            when (buttonOption) {
                is SDGCenterPopupButtonOption.OneOption -> buttonOption.onClick()
                is SDGCenterPopupButtonOption.TwoOption -> buttonOption.onClickCancel()
                is SDGCenterPopupButtonOption.DeleteOption -> buttonOption.onClickCancel()
            }
        },
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(DIALOG_DIM_AMOUNT)

        val containerSize = LocalWindowInfo.current.containerSize
        val density = LocalDensity.current.density
        val screenHeight = containerSize.height.dp / density

        Column(
            modifier = Modifier
                .padding(horizontal = SDGSpacing.Spacing20)
                .heightIn(max = screenHeight - 160.dp)
                .background(
                    color = SDGColor.Neutral0,
                    shape = RoundedCornerShape(SDGCornerRadius.Radius20)
                )
                .padding(top = SDGSpacing.Spacing24)
        ) {
            if (!title.isNullOrBlank()) {
                SDGCenterPopupTitle(
                    modifier = Modifier.padding(horizontal = SDGSpacing.Spacing24),
                    title = title,
                    titleAlignment = titleAlignment
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = SDGSpacing.Spacing24)
                    .weight(1f, false)
                    .verticalScroll(rememberScrollState())
                    .padding(top = SDGSpacing.Spacing4, bottom = SDGSpacing.Spacing28)
            ) {
                body()
            }

            SDGCenterPopupButton(buttonOption)
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
private fun PreviewSDGCenterPopupOneOption() {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = "확인",
            onClick = {},
            labelColor = SDGColor.Neutral700
        ),
        title = "팝업 타이틀",
        body = {
            SDGText(
                text = "팝업 내용입니다. ".repeat(20),
                typography = SDGTypography.Body1R,
                textColor = SDGColor.Neutral700
            )
        }
    )
}

@Preview
@Composable
private fun PreviewSDGCenterPopupTwoOption() {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.TwoOption(
            cancelLabel = "취소",
            confirmLabel = "확인",
            onClickCancel = {},
            onClickConfirm = {},
            cancelLabelColor = SDGColor.Neutral700,
            confirmLabelColor = SDGColor.Neutral700
        ),
        title = "팝업 타이틀",
        titleAlignment = TextAlign.Center,
        body = {
            SDGText(
                text = "팝업 내용입니다. ".repeat(20),
                typography = SDGTypography.Body1R,
                textColor = SDGColor.Neutral700
            )
        }
    )
}

@Preview
@Composable
private fun PreviewSDGCenterPopupDeleteOption() {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.DeleteOption(
            deleteLabel = "삭제",
            cancelLabel = "취소",
            onClickCancel = {},
            onClickDelete = {},
            deleteLabelColor = SDGColor.Red300,
            cancelLabelColor = SDGColor.Neutral700
        ),
        title = "팝업 타이틀",
        body = {
            repeat(40) {
                SDGText(
                    text = "팝업 내용입니다. ",
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700
                )
            }
        }
    )
}
