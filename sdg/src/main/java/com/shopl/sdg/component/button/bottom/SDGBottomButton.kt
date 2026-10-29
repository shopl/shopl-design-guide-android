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
 * @param label 한 줄로 표시하며, 가용 너비를 넘으면 말줄임하는 버튼명
 * @param marginValues 호출부에서 지정하는 외부 여백. 화면 배치 가이드는 좌우 12dp, 하단 10dp를 사용한다.
 * @param debounce 중복 클릭 방지 사용 여부
 * @param debounceTime 중복 클릭 방지 대기 시간. 기본값은 1,000ms이다.
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19481-1283&m=dev">Figma</a>
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SDGBottomButton(
    size: SDGBottomButtonSize,
    style: SDGBottomButtonStyle,
    label: String,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    enabled: Boolean = true,
    debounce: Boolean = true,
    debounceTime: Long = DEFAULT_DEBOUNCE_TIME,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val (activeColor, pressedColor, disabledColor) = when (style) {
        SDGBottomButtonStyle.Primary -> Triple(
            SDGColor.Primary300,
            SDGColor.Primary400,
            SDGColor.Primary50,
        )

        SDGBottomButtonStyle.Point -> Triple(SDGColor.Red300, SDGColor.Red400, SDGColor.Red50)
        SDGBottomButtonStyle.Normal -> Triple(
            SDGColor.Neutral200,
            SDGColor.Neutral300,
            SDGColor.Neutral200,
        )

        SDGBottomButtonStyle.Neutral -> Triple(
            SDGColor.Neutral700,
            SDGColor.Neutral900,
            SDGColor.Neutral300,
        )
    }
    val textColor = when {
        style != SDGBottomButtonStyle.Normal -> SDGColor.Neutral0
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

    val sizeModifier = when (size) {
        SDGBottomButtonSize.Full -> Modifier.fillMaxWidth()
        SDGBottomButtonSize.Adaptive -> Modifier.widthIn(min = BOTTOM_BUTTON_ADAPTIVE_MIN_WIDTH_DP.dp)
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
                text = label,
                textAlign = TextAlign.Center,
                textColor = textColor,
                typography = SDGTypography.Body1R,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * 기존 패키지, 시그니처와 기본값을 유지하는 Bottom Button 호환 API입니다.
 * POSITIVE는 Primary, NEGATIVE는 Point, NORMAL은 Normal, NORMAL_DARK는 Neutral로 변환합니다.
 * 레거시 호출에도 새 가이드의 한 줄 말줄임과 기본·눌림·비활성 색상을 적용합니다.
 * 기존 외부 여백, 활성 상태, 콜백과 중복 클릭 방지 설정은 그대로 전달합니다.
 */
@Deprecated(
    message = "title/spec/type 대신 label/size/style을 사용하는 신규 SDGBottomButton API를 사용하세요.",
)
@Composable
@Suppress("DEPRECATION")
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
    SDGBottomButton(
        size = spec.toSize(),
        style = type.toStyle(),
        label = title,
        onClick = onClick,
        marginValues = marginValues,
        enabled = enabled,
        debounce = debounce,
        debounceTime = debounceTime,
    )
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
