package com.shopl.sdg.component.check_option

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.check_option.preview.SDGCheckOptionPreviewParameterProvider
import com.shopl.sdg.component.check_option.preview.SDGCheckOptionPreviewParams
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/**
 * SDG - Component - Check Option
 *
 * 하나의 옵션을 선택 또는 확인하는 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7349-16748&m=dev">Figma</a>
 */
@Composable
fun SDGCheckOption(
    type: SDGCheckOptionType,
    status: SDGCheckOptionStatus,
    selectedColor: SDGCheckOptionColor = SDGCheckOptionColor.BASIC,
    size: SDGCheckOptionSize = SDGCheckOptionSize.MEDIUM,
) {
    val animatedColor by animateColorAsState(
        targetValue = when(status) {
            SDGCheckOptionStatus.DEFAULT -> SDGColor.Neutral200
            SDGCheckOptionStatus.SELECTED -> selectedColor.color
            SDGCheckOptionStatus.DISABLED -> SDGColor.Neutral200
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "SDGCheckOptionAnimatedColor"
    )

    Box(
        modifier = Modifier
            .size(size.circleSize)
            .clip(shape = CircleShape)
            .then(
                when(type) {
                    SDGCheckOptionType.SOLID -> {
                        Modifier.background(animatedColor)
                    }
                    SDGCheckOptionType.LINE -> {
                        Modifier
                            .border(width = 1.dp, color = animatedColor, shape = CircleShape)
                            .background(SDGColor.Transparent)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        SDGImage(
            modifier = Modifier.size(size.iconSize),
            resId = R.drawable.ic_common_check_s,
            color = when(type) {
                SDGCheckOptionType.SOLID -> SDGColor.Neutral0
                SDGCheckOptionType.LINE -> animatedColor
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGCheckOption(
    @PreviewParameter(SDGCheckOptionPreviewParameterProvider::class)
    params: SDGCheckOptionPreviewParams
) {
    SDGCheckOption(
        type = params.type,
        status = params.status,
        selectedColor = params.selectedColor,
        size = params.size
    )
}
