package com.shopl.sdg.component.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.radio.preview.SDGRadioPreviewParameterProvider
import com.shopl.sdg.component.radio.preview.SDGRadioPreviewParams
import com.shopl.sdg_common.foundation.SDGColor

/**
 * SDG - Component - Radio
 *
 * 여러개의 옵션 중 단일 선택을 위한 컴포넌트
 *
 * @param isSelected 선택 상태
 * @param size 라디오 버튼 크기 (Medium(Default, 16*16) or Large(18*18))
 * @param color 라디오 버튼 색상 (Basic(Default, Primary300) or Special(Neutral700))
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7349-16797&m=dev">Figma</a>
 */
@Composable
fun SDGRadio(
    isSelected: Boolean,
    color: SDGRadioColor = SDGRadioColor.BASIC,
    size: SDGRadioSize = SDGRadioSize.MEDIUM,
) {
    val radioColor = if (isSelected) color.color else SDGColor.Neutral200
    val circleSize = when (size) {
        SDGRadioSize.LARGE -> 18.dp
        SDGRadioSize.MEDIUM -> 16.dp
    }
    val innerCircleSize = circleSize / 2

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = circleSize)
            .background(color = radioColor, shape = CircleShape)
    ) {
        Box(
            modifier = Modifier
                .size(size = innerCircleSize)
                .background(color = SDGColor.Neutral0, shape = CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGRadio(
    @PreviewParameter(SDGRadioPreviewParameterProvider::class)
    params: SDGRadioPreviewParams
) {
    SDGRadio(
        isSelected = params.isSelected,
        size = params.size,
        color = params.color
    )
}
