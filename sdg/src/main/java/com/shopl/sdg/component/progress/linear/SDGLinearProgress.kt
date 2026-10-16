package com.shopl.sdg.component.progress.linear

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.progress.linear.preview.SDGLinearProgressPreviewParameter
import com.shopl.sdg.component.progress.linear.preview.SDGLinearProgressPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private const val INDETERMINATE_DURATION_MILLIS = 1000
private const val INDETERMINATE_WIDTH_FRACTION = 0.5f
private val PROGRESS_WIDTH = 160.dp
private val PROGRESS_BAR_HEIGHT = 4.dp

/**
 * SDG - Progress - Linear Progress
 *
 * @version 2.3.52
 *
 * UI를 불러오거나 사용자가 취한 액션을 처리할 경우 사용하며, 그 처리 과정이 진행 중임을 알려주는 문구가 포함된 바 형태의 컴포넌트
 *
 * @param type 진행률에 따라 채워지는 Determinate 또는 좌우로 반복 이동하는 Indeterminate
 * @param label 진행 상태를 안내하는 문구. null이면 라벨과 하단 간격을 표시하지 않습니다.
 * @param labelColor 라벨 색상
 * @param marginValues 컴포넌트 외부 여백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=29803-7716&m=dev">Figma</a>
 */
@Composable
fun SDGLinearProgress(
    type: SDGLinearProgressType,
    label: String?,
    labelColor: Color,
    marginValues: PaddingValues = PaddingValues(),
) {
    val indicatorPosition = if (type == SDGLinearProgressType.Indeterminate) {
        val transition = rememberInfiniteTransition(label = "SDGLinearProgress")
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(INDETERMINATE_DURATION_MILLIS, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
            label = "SDGLinearProgressPosition",
        )
    } else {
        null
    }

    Column(
        modifier = Modifier
            .padding(marginValues)
            .width(PROGRESS_WIDTH),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(PROGRESS_BAR_HEIGHT)
                .background(SDGColor.Neutral300, SDGCornerRadius.BoxRadius.Radius2),
        ) {
            val indicatorWidth = size.width * when (type) {
                is SDGLinearProgressType.Determinate -> type.progress
                SDGLinearProgressType.Indeterminate -> INDETERMINATE_WIDTH_FRACTION
            }
            val start = (size.width - indicatorWidth) * (indicatorPosition?.value ?: 0f)

            if (indicatorWidth > 0f) {
                drawRoundRect(
                    color = SDGColor.Primary300,
                    topLeft = Offset(start, 0f),
                    size = Size(indicatorWidth, size.height),
                    cornerRadius = CornerRadius(size.height / 2f),
                )
            }
        }

        if (label != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                SDGText(
                    modifier = Modifier.fillMaxWidth(),
                    text = label,
                    typography = SDGTypography.Body3R,
                    textColor = labelColor,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(name = "Font scale 3", showBackground = true, fontScale = 3f)
@Composable
private fun PreviewSDGLinearProgress(
    @PreviewParameter(SDGLinearProgressPreviewParameterProvider::class)
    parameter: SDGLinearProgressPreviewParameter,
) {
    with(parameter) {
        SDGLinearProgress(
            type = type,
            label = label,
            labelColor = labelColor,
        )
    }
}
