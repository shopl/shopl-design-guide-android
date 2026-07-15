package com.shopl.sdg.component.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.toggle.spec.SDGToggleSize
import com.shopl.sdg.component.toggle.spec.SDGToggleSpec
import com.shopl.sdg.component.toggle.style.SDGToggleColors
import com.shopl.sdg.component.toggle.style.SDGToggleStyle
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor

/**
 * SDG - Component - Toggle
 *
 * ON/OFF 상태를 전환하기 위한 컴포넌트
 *
 * @version 2.1.18
 *
 * @param isOn 토글 ON 여부
 * @param modifier Modifier
 * @param isEnabled 토글 활성 여부
 * @param style 토글 스타일 [SDGToggleStyle.Primary|SDGToggleStyle.NEUTRAL]
 * @param style 토글 사이즈 스펙 [SDGToggleSpec.MEDIUM|SDGToggleSpec.SMALL]
 * @param clickPadding 터치 영역 확장용 패딩
 * @param onClick 클릭 이벤트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6869-15142&m=dev">Figma</a>
 */
@Composable
fun SDGToggle(
    isOn: Boolean,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    style: SDGToggleStyle = SDGToggleStyle.PRIMARY,
    spec: SDGToggleSpec = SDGToggleSpec.MEDIUM,
    clickPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    SDGToggleContent(
        isOn = isOn,
        isEnabled = isEnabled,
        modifier = modifier,
        colors = style.colors,
        size = spec.size,
        clickPadding = clickPadding,
        onClick = onClick,
    )
}

@Composable
private fun SDGToggleContent(
    isOn: Boolean,
    isEnabled: Boolean,
    modifier: Modifier,
    colors: SDGToggleColors,
    size: SDGToggleSize,
    clickPadding: PaddingValues,
    onClick: (() -> Unit)?,
) {
    val trackColor by animateColorAsState(
        targetValue = colors.trackColor(isOn = isOn, isEnabled = isEnabled),
        animationSpec = SDGToggleDefaults.COLOR_ANIMATION_SPEC,
        label = "trackColor"
    )
    val thumbColor by animateColorAsState(
        targetValue = colors.thumbColor(isOn = isOn, isEnabled = isEnabled),
        animationSpec = SDGToggleDefaults.COLOR_ANIMATION_SPEC,
        label = "thumbColor"
    )
    val thumbRadius = (size.height / 2) - size.thumbGap
    val thumbCenterX by animateDpAsState(
        targetValue = if (isOn) {
            size.width - thumbRadius - size.thumbGap
        } else {
            thumbRadius + size.thumbGap
        },
        animationSpec = SDGToggleDefaults.DP_ANIMATION_SPEC,
        label = "thumbCenterX"
    )

    Canvas(
        modifier = modifier
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        enabled = isEnabled,
                        role = Role.Switch,
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            )
            .semantics {
                toggleableState = if (isOn) {
                    ToggleableState.On
                } else {
                    ToggleableState.Off
                }
            }
            .padding(clickPadding)
            .size(
                width = size.width,
                height = size.height
            )
    ) {
        drawRoundRect(
            color = trackColor,
            cornerRadius = CornerRadius(
                x = size.trackRadius.toPx(),
                y = size.trackRadius.toPx()
            )
        )
        drawCircle(
            color = thumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = thumbCenterX.toPx(),
                y = this.size.height / 2
            )
        )
    }
}

private fun SDGToggleColors.trackColor(
    isOn: Boolean,
    isEnabled: Boolean,
): Color = when {
    isOn && isEnabled -> onTrackColor
    isOn && !isEnabled -> disabledOnTrackColor
    !isOn && isEnabled -> offTrackColor
    else -> disabledOffTrackColor
}

private fun SDGToggleColors.thumbColor(
    isOn: Boolean,
    isEnabled: Boolean,
): Color = when {
    isOn && isEnabled -> onThumbColor
    isOn && !isEnabled -> disabledOnThumbColor
    !isOn && isEnabled -> offThumbColor
    else -> disabledOffThumbColor
}

private object SDGToggleDefaults {
    const val LEGACY_DISABLED_ALPHA = 0.3f
    private const val ANIMATION_DURATION_MILLIS = 300

    val COLOR_ANIMATION_SPEC = tween<Color>(
        durationMillis = ANIMATION_DURATION_MILLIS,
        easing = FastOutSlowInEasing
    )
    val DP_ANIMATION_SPEC = tween<Dp>(
        durationMillis = ANIMATION_DURATION_MILLIS,
        easing = FastOutSlowInEasing
    )
}

@Deprecated(
    message = "신규 SDGToggle(isOn, isEnabled, onClick)를 사용하세요.",
    replaceWith = ReplaceWith(
        expression = "SDGToggle(isOn = isChecked, onClick = onCheckedChange)",
        imports = ["com.shopl.sdg.component.toggle.SDGToggle"]
    )
)
@Composable
fun SDGToggle(
    isChecked: Boolean,
    onCheckedChange: (() -> Unit)?,
    checkedTrackColor: Color = SDGColor.Primary300,
    uncheckedTrackColor: Color = SDGColor.Neutral300,
    thumbColor: Color = SDGColor.Neutral0,
    gapBetweenThumbAndTrackEdge: Dp = 2.dp,
    clickPadding: PaddingValues = PaddingValues(),
) {
    SDGToggleContent(
        isOn = isChecked,
        isEnabled = true,
        modifier = Modifier,
        colors = SDGToggleColors(
            onTrackColor = checkedTrackColor,
            onThumbColor = thumbColor,
            offTrackColor = uncheckedTrackColor,
            offThumbColor = thumbColor,
            disabledOnTrackColor = checkedTrackColor.copy(alpha = SDGToggleDefaults.LEGACY_DISABLED_ALPHA),
            disabledOnThumbColor = thumbColor.copy(alpha = SDGToggleDefaults.LEGACY_DISABLED_ALPHA),
            disabledOffTrackColor = uncheckedTrackColor.copy(alpha = SDGToggleDefaults.LEGACY_DISABLED_ALPHA),
            disabledOffThumbColor = thumbColor.copy(alpha = SDGToggleDefaults.LEGACY_DISABLED_ALPHA),
        ),
        size = SDGToggleSpec.MEDIUM.size,
        clickPadding = clickPadding,
        onClick = onCheckedChange,
    )
}

@Preview
@Composable
private fun PreviewSDGToggle(
    @PreviewParameter(SDGTogglePreviewParameterProvider::class) parameter: SDGTogglePreviewParameter,
) {
    SDGToggle(
        isOn = parameter.isOn,
        style = parameter.style,
        spec = parameter.spec,
        isEnabled = parameter.isEnabled
    )
}
