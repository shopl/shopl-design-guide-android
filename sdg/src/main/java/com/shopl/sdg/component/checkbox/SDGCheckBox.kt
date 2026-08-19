package com.shopl.sdg.component.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSelectedBackgroundColor
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSize
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxState
import com.shopl.sdg.component.checkbox.preview.SDGCheckBoxPreviewParameterProvider
import com.shopl.sdg.component.checkbox.preview.SDGCheckBoxPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/**
 * SDG - Component - Checkbox
 *
 * 여러 옵션 중 하나 이상을 자유롭게 복수 선택하거나, 단일 항목의 활성화(On/Off) 상태를 토글하기 위한 컨트롤 컴포넌트
 *
 * @version 2.3.41
 *
 * @param state 체크박스 상태
 * @param selectedBackgroundColor [SDGCheckBoxState.SELECTED] 상태에 적용할 배경 색상
 * @param size 체크박스 크기
 * @param onClick 체크박스 클릭 콜백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27349-42162&m=dev">Figma</a>
 */
@Composable
fun SDGCheckBox(
    state: SDGCheckBoxState,
    selectedBackgroundColor: SDGCheckBoxSelectedBackgroundColor,
    size: SDGCheckBoxSize,
    onClick: () -> Unit,
) {
    val (checkBoxColor, clickable) = when (state) {
        SDGCheckBoxState.DEFAULT -> SDGColor.Neutral250 to Modifier.clickable(onClick = onClick)
        SDGCheckBoxState.SELECTED -> selectedBackgroundColor.selectedBackgroundColor to Modifier.clickable(onClick = onClick)
        SDGCheckBoxState.DISABLED -> SDGColor.Neutral200 to Modifier
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size.size)
            .background(
                color = checkBoxColor,
                shape = SDGCornerRadius.BoxRadius.Radius4,
            )
            .then(other = clickable),
    ) {
        SDGImage(
            resId = R.drawable.ic_common_check_s,
            color = SDGColor.Neutral0,
            modifier = Modifier.size(size = 14.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGCheckBox(
    @PreviewParameter(provider = SDGCheckBoxPreviewParameterProvider::class)
    params: SDGCheckBoxPreviewParams,
) {
    SDGCheckBox(
        state = params.state,
        selectedBackgroundColor = params.selectedColor,
        size = params.size,
        onClick = {},
    )
}

@Deprecated("v2.3.41 이상 SDGCheckBox를 사용하세요.")
@Composable
fun SDGCheckBox(
    isChecked: Boolean,
    enabled: Boolean = true,
    checkedBackgroundColor: Color = SDGColor.Primary300,
    clickPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isChecked && enabled -> checkedBackgroundColor
            enabled -> SDGColor.Neutral250
            else -> SDGColor.Neutral200
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(clickPadding)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        hasRipple = false,
                        onClick = { onClick() }
                    )
                } else {
                    Modifier
                }
            )
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = backgroundColor)
    ) {

        Image(
            modifier = Modifier
                .padding(1.dp)
                .wrapContentSize(align = Alignment.Center),
            painter = painterResource(
                id = R.drawable.ic_common_check_s,
            ),
            colorFilter = ColorFilter.tint(SDGColor.Neutral0),
            contentDescription = ""
        )

    }
}
