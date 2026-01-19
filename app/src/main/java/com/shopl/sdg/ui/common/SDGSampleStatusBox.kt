package com.shopl.sdg.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.template.radio_label.SDGRadioLabel
import com.shopl.sdg.template.radio_label.SDGRadioLabelStatus
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG 샘플에서 Status 선택을 위한 Box
 * 해당 박스 내부에 요소를 그리는 Content 영역이 존재함
 */
@Composable
internal fun SDGSampleStatusBox(
    currentStatus: SDGSampleStatus,
    onClickStatus: (SDGSampleStatus) -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(SDGCornerRadius.BoxRadius.Radius8)
            .border(
                width = 1.dp,
                color = SDGColor.Neutral200,
                shape = SDGCornerRadius.BoxRadius.Radius8
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SDGSpacing.Spacing16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1F)) {
                SDGRadioLabel(
                    label = SDGSampleStatus.DEFAULT.displayLabel,
                    status = if(currentStatus == SDGSampleStatus.DEFAULT) {
                        SDGRadioLabelStatus.SELECTED
                    } else SDGRadioLabelStatus.DEFAULT,
                    radioColor = SDGRadioColor.SPECIAL,
                    onClick = { onClickStatus(SDGSampleStatus.DEFAULT) },
                )
            }
            Box(modifier = Modifier.weight(1F)) {
                SDGRadioLabel(
                    label = SDGSampleStatus.DISABLED.displayLabel,
                    status = if(currentStatus == SDGSampleStatus.DISABLED) {
                        SDGRadioLabelStatus.SELECTED
                    } else SDGRadioLabelStatus.DEFAULT,
                    radioColor = SDGRadioColor.SPECIAL,
                    onClick = { onClickStatus(SDGSampleStatus.DISABLED) },
                )
            }
        }
        HorizontalDivider(color = SDGColor.Neutral200)
        content()
    }
}

@Preview
@Composable
private fun PreviewSDGSampleStatusBox() {
    SDGSampleStatusBox(
        currentStatus = SDGSampleStatus.DEFAULT,
        onClickStatus = {},
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(SDGColor.Neutral150)
            ) {
                SDGText(
                    modifier = Modifier.align(Alignment.Center),
                    text = "SDG Status Box Content",
                    textColor = SDGColor.Neutral700,
                    typography = SDGTypography.Body3R
                )
            }
        }
    )
}