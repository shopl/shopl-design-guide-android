package com.shopl.sdg.template.popup.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.popup.SDGPopup
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Popup - Icon Popup
 *
 * 정보의 강조 또는 도움 요소로 아이콘을 포함하는 화면 중앙에 노출되는 팝업
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19226-7663">Figma</a>
 */
@Composable
fun SDGIconPopup(
    @DrawableRes resId: Int,
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickConfirm: () -> Unit,
    title: String,
    titleTextColor: Color = SDGColor.Neutral700,
    titleAlign: SDGIconPopupAlign = SDGIconPopupAlign.CENTER,
    body: String? = null,
    bodyTextColor: Color = SDGColor.Neutral600,
    bodyTypography: SDGTypography = SDGTypography.Body1R,
    bodyAlign: SDGIconPopupAlign = SDGIconPopupAlign.CENTER
) {
    SDGPopup(
        singleButton = true,
        confirmLabel = confirmLabel,
        onClickConfirm = onClickConfirm
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)
        ) {
            SDGImage(
                modifier = Modifier
                    .size(75.dp),
                resId = resId,
                color = null
            )
            SDGText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                typography = SDGTypography.Title2SB,
                textColor = titleTextColor,
                textAlign = when(titleAlign) {
                    SDGIconPopupAlign.CENTER -> TextAlign.Center
                    SDGIconPopupAlign.LEFT -> TextAlign.Start
                }
            )
            body?.let {
                SDGText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = SDGSpacing.Spacing4
                        ),
                    text = it,
                    typography = bodyTypography,
                    textColor = bodyTextColor,
                    textAlign = when(bodyAlign) {
                        SDGIconPopupAlign.CENTER -> TextAlign.Center
                        SDGIconPopupAlign.LEFT -> TextAlign.Start
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewSDGIconPopup() {
    SDGIconPopup(
        resId = R.drawable.popup_ic_warning,
        onClickConfirm = {},
        title = "기록을 남길 수 없습니다.",
        body = "정시 출근 인정 범위가 적용되어 10:00 에 출근한 것으로 처리되었습니다.\n10:00 이후에 다시 시도해주세요."
    )
}