package com.shopl.sdg.template.popup.center

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg.template.popup.center.preview.SDGCenterPopupParameterProvider
import com.shopl.sdg.template.popup.center.preview.SDGCenterPopupPreviewData
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing24
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
    body: @Composable (ColumnScope.() -> Unit)?,
) {
    Dialog(
        onDismissRequest = {
            when (buttonOption) {
                is SDGCenterPopupButtonOption.OneOption -> buttonOption.onClick()
                is SDGCenterPopupButtonOption.TwoOption -> buttonOption.run { onDismiss?.let { it() } ?: onClickCancel() }
                is SDGCenterPopupButtonOption.DeleteOption -> buttonOption.run { onDismiss?.let { it() } ?: onClickCancel() }
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
                .padding(top = Spacing24)
        ) {
            if (!title.isNullOrBlank()) {
                SDGCenterPopupTitle(
                    modifier = Modifier
                        .padding(horizontal = Spacing24)
                        .padding(bottom = if (body == null) Spacing24 else Spacing12),
                    title = title,
                    titleAlignment = titleAlignment
                )
            }

            body?.let {
                Column(
                    modifier = Modifier
                        .padding(horizontal = Spacing24)
                        .weight(1f, false)
                        .verticalScroll(rememberScrollState())
                        .padding(top = SDGSpacing.Spacing4, bottom = SDGSpacing.Spacing28)
                ) {
                    it()
                }
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
private fun PreviewSDGCenterPopup(
    @PreviewParameter(SDGCenterPopupParameterProvider::class)
    data: SDGCenterPopupPreviewData
) {
    SDGCenterPopup(
        buttonOption = data.buttonOption,
        title = data.title,
        titleAlignment = data.titleAlignment,
        body = data.body
    )
}
