package com.shopl.sdg.template.popup.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.SDGButtonFontWeight
import com.shopl.sdg.component.button.ghost.SDGGhostButtonSize
import com.shopl.sdg.component.util.button.ghost.SDGGhostButton
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

private val SDGBottomPopupButtonHeight = 58.dp

/**
 * SDG - Template - Popup - bottom popup button
 *
 * @version 2.3.32
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19270-15961&m=dev">Figma</a>
 */
@Composable
fun SDGBottomPopupButton(option: SDGBottomPopupButtonOption) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SDGColor.Neutral0)
    ) {
        HorizontalDivider(color = SDGColor.Neutral200)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(SDGBottomPopupButtonHeight),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when (option) {
                is SDGBottomPopupButtonOption.OneOption -> {
                    SDGOneOptionBottomPopupButton(
                        label = option.label,
                        labelColor = option.labelColor,
                        onClick = option.onClick,
                    )
                }

                is SDGBottomPopupButtonOption.TwoOption -> {
                    SDGTwoOptionBottomPopupButton(
                        startLabel = option.startLabel,
                        startLabelColor = option.startLabelColor,
                        onClickStart = option.onClickStart,
                        endLabel = option.endLabel,
                        endLabelColor = option.endLabelColor,
                        onClickEnd = option.onClickEnd,
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.SDGOneOptionBottomPopupButton(
    label: String,
    labelColor: Color,
    onClick: () -> Unit,
) {
    SDGGhostButton(
        weight = 1f,
        size = SDGGhostButtonSize.Large,
        label = label,
        labelColor = labelColor,
        onClick = onClick,
        labelWeight = SDGButtonFontWeight.SB,
        marginValues = PaddingValues(vertical = SDGSpacing.Spacing8)
    )
}

@Composable
private fun RowScope.SDGTwoOptionBottomPopupButton(
    startLabel: String,
    startLabelColor: Color,
    onClickStart: () -> Unit,
    endLabel: String,
    endLabelColor: Color,
    onClickEnd: () -> Unit,
) {
    SDGGhostButton(
        weight = 1f,
        size = SDGGhostButtonSize.Large,
        label = startLabel,
        labelColor = startLabelColor,
        onClick = onClickStart,
        labelWeight = SDGButtonFontWeight.R,
        marginValues = PaddingValues(vertical = SDGSpacing.Spacing8)
    )

    VerticalDivider(
        modifier = Modifier.size(width = 1.dp, height = 18.dp),
        color = SDGColor.Neutral150,
    )

    SDGGhostButton(
        weight = 1f,
        size = SDGGhostButtonSize.Large,
        label = endLabel,
        labelColor = endLabelColor,
        onClick = onClickEnd,
        labelWeight = SDGButtonFontWeight.SB,
        marginValues = PaddingValues(vertical = SDGSpacing.Spacing8)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGOneOptionBottomPopupButton() {
    Row {
        SDGOneOptionBottomPopupButton(
            label = "Label",
            labelColor = SDGColor.Neutral700,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGTwoOptionBottomPopupButton() {
    Row {
        SDGTwoOptionBottomPopupButton(
            startLabel = "Label",
            startLabelColor = SDGColor.Red300,
            onClickStart = {},
            endLabel = "Label",
            endLabelColor = SDGColor.Neutral700,
            onClickEnd = {},
        )
    }
}
