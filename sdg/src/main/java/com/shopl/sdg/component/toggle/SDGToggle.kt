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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    clickPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    SDGToggleContent(
        isOn = isOn,
        isEnabled = isEnabled,
        modifier = modifier,
        colors = style.colors,
        thumbGap = SDGToggleDefaults.THUMB_GAP,
        clickPadding = clickPadding,
        onClick = onClick,
    )
}

enum class SDGToggleStyle(
    internal val colors: SDGToggleColors,
) {
    PRIMARY(
        colors = SDGToggleColors(
            onTrackColor = SDGColor.Primary300,
            onThumbColor = SDGColor.Neutral0,
            offTrackColor = SDGColor.Neutral300,
            offThumbColor = SDGColor.Neutral0,
            disabledOnTrackColor = SDGColor.Neutral200,
            disabledOnThumbColor = SDGColor.Neutral0,
            disabledOffTrackColor = SDGColor.Neutral200,
            disabledOffThumbColor = SDGColor.Neutral0,
        )
    ),
    NEUTRAL(
        colors = SDGToggleColors(
            onTrackColor = SDGColor.Neutral700,
            onThumbColor = SDGColor.Neutral0,
            offTrackColor = SDGColor.Neutral300,
            offThumbColor = SDGColor.Neutral0,
            disabledOnTrackColor = SDGColor.Neutral200,
            disabledOnThumbColor = SDGColor.Neutral0,
            disabledOffTrackColor = SDGColor.Neutral200,
            disabledOffThumbColor = SDGColor.Neutral0,
        )
    )
}

internal data class SDGToggleColors(
    val onTrackColor: Color,
    val onThumbColor: Color,
    val offTrackColor: Color,
    val offThumbColor: Color,
    val disabledOnTrackColor: Color,
    val disabledOnThumbColor: Color,
    val disabledOffTrackColor: Color,
    val disabledOffThumbColor: Color,
)

@Composable
private fun SDGToggleContent(
    isOn: Boolean,
    isEnabled: Boolean,
    modifier: Modifier,
    colors: SDGToggleColors,
    thumbGap: Dp,
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
    val thumbRadius = (SDGToggleDefaults.HEIGHT / 2) - thumbGap
    val thumbCenterX by animateDpAsState(
        targetValue = if (isOn) {
            SDGToggleDefaults.WIDTH - thumbRadius - thumbGap
        } else {
            thumbRadius + thumbGap
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
                width = SDGToggleDefaults.WIDTH,
                height = SDGToggleDefaults.HEIGHT
            )
    ) {
        drawRoundRect(
            color = trackColor,
            cornerRadius = CornerRadius(
                x = SDGToggleDefaults.TRACK_RADIUS.toPx(),
                y = SDGToggleDefaults.TRACK_RADIUS.toPx()
            )
        )
        drawCircle(
            color = thumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = thumbCenterX.toPx(),
                y = size.height / 2
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
    val WIDTH = 40.dp
    val HEIGHT = 22.dp
    val TRACK_RADIUS = 100.dp
    val THUMB_GAP = 2.dp
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
        thumbGap = gapBetweenThumbAndTrackEdge,
        clickPadding = clickPadding,
        onClick = onCheckedChange,
    )
}

@Preview(name = "Primary", showBackground = true)
@Composable
private fun PreviewSDGTogglePrimary() {
    var isOn by remember { mutableStateOf(false) }

    SDGToggle(
        isOn = isOn,
        style = SDGToggleStyle.PRIMARY,
        clickPadding = PaddingValues(20.dp),
        onClick = { isOn = !isOn }
    )
}

@Preview(name = "Neutral", showBackground = true)
@Composable
private fun PreviewSDGToggleNeutral() {
    var isOn by remember { mutableStateOf(false) }

    SDGToggle(
        isOn = isOn,
        style = SDGToggleStyle.NEUTRAL,
        clickPadding = PaddingValues(20.dp),
        onClick = { isOn = !isOn }
    )
}
