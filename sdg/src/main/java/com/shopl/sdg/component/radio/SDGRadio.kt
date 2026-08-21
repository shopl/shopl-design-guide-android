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
import com.shopl.sdg.component.radio.model.SDGRadioSelectedBackgroundColor
import com.shopl.sdg.component.radio.model.SDGRadioSize
import com.shopl.sdg.component.radio.model.SDGRadioState
import com.shopl.sdg.component.radio.preview.SDGRadioPreviewParameterProvider
import com.shopl.sdg.component.radio.preview.SDGRadioPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/**
 * SDG - Component - Radio
 *
 * 여러 상호 배타적인 선택지 중 사용자가 단 하나의 옵션만 명확하게 확정할 수 있도록 제어하는 원형 인디케이터 컴포넌트
 *
 * @version 2.3.41
 *
 * @param state 라디오 버튼 상태
 * @param selectedBackgroundColor [SDGRadioState.SELECTED] 상태에 적용할 배경 색상
 * @param size 라디오 버튼 크기
 * @param onClick 라디오 버튼 클릭 콜백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27346-40022&m=dev">Figma</a>
 */
@Composable
fun SDGRadio(
    state: SDGRadioState,
    selectedBackgroundColor: SDGRadioSelectedBackgroundColor,
    size: SDGRadioSize,
    onClick: () -> Unit,
) {
    val (radioColor, radioEnabled) = when (state) {
        SDGRadioState.DEFAULT -> SDGColor.Neutral250 to true
        SDGRadioState.SELECTED -> selectedBackgroundColor.selectedBackgroundColor to true
        SDGRadioState.DISABLED -> SDGColor.Neutral200 to false
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size.circleSize)
            .background(
                color = radioColor,
                shape = CircleShape,
            )
            .clickable(
                enabled = radioEnabled,
                onClick = onClick,
            ),
    ) {
        SDGImage(
            resId = R.drawable.ic_common_circle_s,
            color = SDGColor.Neutral0,
            modifier = Modifier.size(size = size.iconSize),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGRadio(
    @PreviewParameter(provider = SDGRadioPreviewParameterProvider::class)
    params: SDGRadioPreviewParams,
) {
    SDGRadio(
        state = params.state,
        selectedBackgroundColor = params.selectedColor,
        size = params.size,
        onClick = {},
    )
}
