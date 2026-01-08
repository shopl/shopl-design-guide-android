package com.shopl.sdg.template.radio_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.radio.SDGRadio
import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.component.radio.SDGRadioSize
import com.shopl.sdg.template.radio_label.preview.SDGRadioLabelPreviewParameterProvider
import com.shopl.sdg.template.radio_label.preview.SDGRadioLabelPreviewParams
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Template - Radio Label
 *
 * 여러개의 옵션 중 단일 선택을 위한 Radio와 Label이 조합된 템플릿
 *
 * @param isSelected 라디오 선택 상태
 * @param label 라디오 옆에 표시되는 텍스트 라벨
 * @param isEnabled 컴포넌트 활성화 여부
 * @param labelColor 라벨 텍스트 색상 타입
 * @param radioColor 라디오 버튼 색상 타입
 * @param radioSize 라디오 버튼 크기
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19392-12559&m=dev">Figma</a>
 */
@Composable
fun SDGRadioLabel(
    isSelected: Boolean,
    label: String,
    isEnabled: Boolean = true,
    labelColor: SDGRadioLabelColor = SDGRadioLabelColor.BASIC,
    radioColor: SDGRadioColor = SDGRadioColor.BASIC,
    radioSize: SDGRadioSize = SDGRadioSize.MEDIUM,
) {
    val labelTextColor = when {
        !isEnabled -> SDGColor.Neutral300
        isSelected -> labelColor.color
        else -> SDGColor.Neutral300
    }
    val typography = SDGTypography.Body1R
    val density = LocalDensity.current
    val lineHeightDp = with(receiver = density) { typography.style.lineHeight.toDp() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Spacing8),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier.height(height = lineHeightDp),
            contentAlignment = Alignment.Center
        ) {
            SDGRadio(
                isSelected = isSelected,
                isEnabled = isEnabled,
                color = radioColor,
                size = radioSize,
            )
        }

        SDGText(
            text = label,
            textColor = labelTextColor,
            typography = typography,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGRadioLabel(
    @PreviewParameter(SDGRadioLabelPreviewParameterProvider::class)
    params: SDGRadioLabelPreviewParams
) {
    SDGRadioLabel(
        isSelected = params.isSelected,
        isEnabled = params.isEnabled,
        label = params.label,
        labelColor = params.labelColor,
    )
}
