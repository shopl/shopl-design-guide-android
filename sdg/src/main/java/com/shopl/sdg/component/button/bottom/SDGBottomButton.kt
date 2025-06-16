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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Button - Bottom Button
 *
 * 화면 하단에 고정으로 위치한 풀사이즈 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7097-15078&m=dev">Figma</a>
 */
@Composable
fun SDGBottomButton(
    title: String,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true
) {
    val (activeColor, disabledColor, textColor) = when (type) {
        SDGBottomButtonType.POSITIVE -> Triple(SDGColor.Primary300, SDGColor.Primary50, SDGColor.Neutral0)
        SDGBottomButtonType.NEGATIVE -> Triple(SDGColor.Red300, SDGColor.Red50, SDGColor.Neutral0)
        SDGBottomButtonType.NORMAL -> Triple(SDGColor.Neutral200, SDGColor.Neutral200, SDGColor.Neutral700)
        SDGBottomButtonType.NORMAL_DARK -> Triple(SDGColor.Neutral600, SDGColor.Neutral200, SDGColor.Neutral0)
    }

    Button(
        enabled = enabled,
        shape = RoundedCornerShape(25.dp),
        onClick = onClick,
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

@Preview
@Composable
fun PreviewBottomButton() {
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGBottomButton(
                title = "POSITIVE",
                type = SDGBottomButtonType.POSITIVE,
                onClick = {}
            )
            SDGBottomButton(
                title = "NEGATIVE",
                type = SDGBottomButtonType.NEGATIVE,
                onClick = {}
            )
            SDGBottomButton(
                title = "NORMAL",
                type = SDGBottomButtonType.NORMAL,
                onClick = {}
            )
            SDGBottomButton(
                title = "NORMAL DARK",
                type = SDGBottomButtonType.NORMAL_DARK,
                onClick = {}
            )
        }
    }
}