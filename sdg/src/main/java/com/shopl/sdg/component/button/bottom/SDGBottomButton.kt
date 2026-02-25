package com.shopl.sdg.component.button.bottom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.bottom.preview.SDGBottomButtonPreviewParameter
import com.shopl.sdg.component.button.bottom.preview.SDGBottomButtonPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.coroutines.delay

/**
 * SDG - Button - Bottom Button [2.0.0]
 *
 * 화면 하단에 고정으로 위치한 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19481-1283&m=dev">Figma</a>
 */

private const val DEFAULT_DEBOUNCE_TIME = 1000L
private const val FULL_WIDTH_FRACTION = 1f
private const val MAX_TITLE_LINES = 2
private const val BOTTOM_BUTTON_HEIGHT_DP = 50
private const val BOTTOM_BUTTON_CORNER_RADIUS_DP = 25
private const val BOTTOM_BUTTON_ADAPTIVE_MIN_WIDTH_DP = 80
private const val BOTTOM_BUTTON_HORIZONTAL_PADDING_DP = 16
private const val BOTTOM_BUTTON_VERTICAL_PADDING_DP = 4

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
    val (activeColor, disabledColor, textColor) = when (type) {
        SDGBottomButtonType.POSITIVE -> Triple(
            SDGColor.Primary300,
            SDGColor.Primary50,
            SDGColor.Neutral0
        )

        SDGBottomButtonType.NEGATIVE -> Triple(SDGColor.Red300, SDGColor.Red50, SDGColor.Neutral0)
        SDGBottomButtonType.NORMAL -> Triple(
            SDGColor.Neutral200,
            SDGColor.Neutral200,
            SDGColor.Neutral700
        )

        SDGBottomButtonType.NORMAL_DARK -> Triple(
            SDGColor.Neutral600,
            SDGColor.Neutral200,
            SDGColor.Neutral0
        )
    }

    val debouncedClick = if (debounce) {
        rememberDebouncedClick(
            delayMs = debounceTime
        ) { onClick() }
    } else {
        onClick
    }

    val sizeModifier = when (spec) {
        SDGBottomButtonSpec.FULL -> Modifier.fillMaxWidth(FULL_WIDTH_FRACTION)
        SDGBottomButtonSpec.ADAPTIVE -> Modifier.widthIn(min = BOTTOM_BUTTON_ADAPTIVE_MIN_WIDTH_DP.dp)
    }

    Button(
        enabled = enabled,
        shape = RoundedCornerShape(BOTTOM_BUTTON_CORNER_RADIUS_DP.dp),
        onClick = debouncedClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = activeColor,
            disabledContainerColor = disabledColor
        ),
        contentPadding = PaddingValues(
            horizontal = BOTTOM_BUTTON_HORIZONTAL_PADDING_DP.dp,
            vertical = BOTTOM_BUTTON_VERTICAL_PADDING_DP.dp
        ),
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
            maxLines = MAX_TITLE_LINES,
            overflow = TextOverflow.Ellipsis,
        )
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

@Preview
@Composable
private fun PreviewBottomButton(
    @PreviewParameter(SDGBottomButtonPreviewParameterProvider::class)
    param: SDGBottomButtonPreviewParameter
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
