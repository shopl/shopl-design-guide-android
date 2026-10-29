package com.shopl.sdg.component.button.bottom

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.bottom.preview.SDGBottomButtonPreviewParameter
import com.shopl.sdg.component.button.bottom.preview.SDGBottomButtonPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.coroutines.delay

private const val DEFAULT_DEBOUNCE_TIME = 1000L
private const val BOTTOM_BUTTON_HEIGHT_DP = 50
private const val BOTTOM_BUTTON_CORNER_RADIUS_DP = 25
private const val BOTTOM_BUTTON_ADAPTIVE_MIN_WIDTH_DP = 80

/**
 * SDG - Button - Bottom Button
 *
 * 화면 하단에 고정으로 위치한 버튼 컴포넌트
 *
 * @version 2.0.0
 * @param title 한 줄로 표시하며, 가용 너비를 넘으면 말줄임하는 버튼명
 * @param marginValues 호출부에서 지정하는 외부 여백. 화면 배치 가이드는 좌우 12dp, 하단 10dp를 사용한다.
 * @param type POSITIVE는 Primary, NORMAL_DARK는 Neutral, NEGATIVE는 Point, NORMAL은 Normal에 대응한다.
 * Neutral 배경은 기본 Neutral700, 눌림 Neutral900, 비활성 Neutral300이며 Normal 비활성 라벨은 Neutral350이다.
 * @param debounce 중복 클릭 방지 사용 여부. 기존 기본값과 대기 시간을 유지한다.
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19481-1283&m=dev">Figma</a>
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGBottomButton(
    title: String,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    spec: SDGBottomButtonSpec = SDGBottomButtonSpec.FULL,
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    debounce: Boolean = true,
    debounceTime: Long = DEFAULT_DEBOUNCE_TIME,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val (activeColor, pressedColor, disabledColor) = when (type) {
        SDGBottomButtonType.POSITIVE -> Triple(
            SDGColor.Primary300,
            SDGColor.Primary400,
            SDGColor.Primary50,
        )

        SDGBottomButtonType.NEGATIVE -> Triple(SDGColor.Red300, SDGColor.Red400, SDGColor.Red50)
        SDGBottomButtonType.NORMAL -> Triple(
            SDGColor.Neutral200,
            SDGColor.Neutral300,
            SDGColor.Neutral200,
        )

        SDGBottomButtonType.NORMAL_DARK -> Triple(
            SDGColor.Neutral700,
            SDGColor.Neutral900,
            SDGColor.Neutral300,
        )
    }
    val textColor = when {
        type != SDGBottomButtonType.NORMAL -> SDGColor.Neutral0
        enabled -> SDGColor.Neutral700
        else -> SDGColor.Neutral350
    }

    val debouncedClick = if (debounce) {
        rememberDebouncedClick(
            delayMs = debounceTime
        ) { onClick() }
    } else {
        onClick
    }

    val sizeModifier = when (spec) {
        SDGBottomButtonSpec.FULL -> Modifier.fillMaxWidth()
        SDGBottomButtonSpec.ADAPTIVE -> Modifier.widthIn(min = BOTTOM_BUTTON_ADAPTIVE_MIN_WIDTH_DP.dp)
    }

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        Button(
            enabled = enabled,
            shape = RoundedCornerShape(BOTTOM_BUTTON_CORNER_RADIUS_DP.dp),
            onClick = debouncedClick,
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (pressed) pressedColor else activeColor,
                disabledContainerColor = disabledColor,
            ),
            contentPadding = PaddingValues(horizontal = SDGSpacing.Spacing16),
            modifier = Modifier
                .padding(marginValues)
                .then(sizeModifier)
                .height(BOTTOM_BUTTON_HEIGHT_DP.dp),
        ) {
            SDGText(
                text = title,
                textAlign = TextAlign.Center,
                textColor = textColor,
                typography = SDGTypography.Body1R,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun rememberDebouncedClick(
    delayMs: Long = DEFAULT_DEBOUNCE_TIME,
    onClick: () -> Unit
): () -> Unit {
    var enabled by remember { mutableStateOf(true) }

    LaunchedEffect(enabled) {
        if (!enabled) {
            delay(delayMs)
            enabled = true
        }
    }

    return {
        if (enabled) {
            enabled = false
            onClick()
        }
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun PreviewBottomButton(
    @PreviewParameter(SDGBottomButtonPreviewParameterProvider::class)
    param: SDGBottomButtonPreviewParameter
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        SDGBottomButton(
            title = param.title,
            onClick = {},
            marginValues = param.marginValues,
            spec = param.spec,
            type = param.type,
            enabled = param.enabled,
            debounce = false,
        )
    }
}
