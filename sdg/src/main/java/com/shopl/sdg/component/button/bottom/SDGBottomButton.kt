package com.shopl.sdg.component.button.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.coroutines.delay

/**
 * SDG - Button - Bottom Button
 *
 * 화면 하단에 고정으로 위치한 풀사이즈 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7097-15078&m=dev">Figma</a>
 */

private const val DEFAULT_DEBOUNCE_TIME = 1000L

@Composable
fun SDGBottomButton(
    title: String,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    debounce: Boolean = true,
    debounceTime: Long = DEFAULT_DEBOUNCE_TIME,
) {
    val (activeColor, disabledColor, textColor) = when (type) {
        SDGBottomButtonType.POSITIVE -> Triple(SDGColor.Primary300, SDGColor.Primary50, SDGColor.Neutral0)
        SDGBottomButtonType.NEGATIVE -> Triple(SDGColor.Red300, SDGColor.Red50, SDGColor.Neutral0)
        SDGBottomButtonType.NORMAL -> Triple(SDGColor.Neutral200, SDGColor.Neutral200, SDGColor.Neutral700)
        SDGBottomButtonType.NORMAL_DARK -> Triple(SDGColor.Neutral600, SDGColor.Neutral200, SDGColor.Neutral0)
    }

    val debouncedClick = if (debounce) {
        rememberDebouncedClick(
            delayMs = debounceTime
        ) { onClick() }
    } else {
        onClick
    }

    Button(
        enabled = enabled,
        shape = RoundedCornerShape(25.dp),
        onClick = debouncedClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = activeColor,
            disabledContainerColor = disabledColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth(1f)
            .height(50.dp),
    ) {
        SDGText(
            text = title,
            textAlign = TextAlign.Center,
            textColor = textColor,
            typography = SDGTypography.Body1R,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
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
fun PreviewBottomButton() {
    var clickCount by remember { mutableIntStateOf(0) }
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGBottomButton(
                title = "POSITIVE",
                type = SDGBottomButtonType.POSITIVE,
                onClick = { clickCount++ }
            )
            SDGBottomButton(
                title = "NEGATIVE",
                type = SDGBottomButtonType.NEGATIVE,
                onClick = { clickCount++ }
            )
            SDGBottomButton(
                title = "NORMAL",
                type = SDGBottomButtonType.NORMAL,
                onClick = { clickCount++ }
            )
            SDGBottomButton(
                title = "NORMAL DARK",
                type = SDGBottomButtonType.NORMAL_DARK,
                debounce = false,
                onClick = { clickCount++ }
            )
            SDGText(
                text = "Click Count : $clickCount",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1R
            )
        }
    }
}