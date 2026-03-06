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
                        onClick = option.onClick,
                    )
                }

                is SDGBottomPopupButtonOption.TwoOption -> {
                    SDGTwoOptionBottomPopupButton(
                        startLabel = option.startLabel,
                        onClickStart = option.onClickStart,
                        endLabel = option.endLabel,
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
    onClick: () -> Unit,
) {
    SDGGhostButton(
        weight = 1f,
        size = SDGGhostButtonSize.Large,
        label = label,
        labelColor = SDGColor.Neutral700,
        onClick = onClick,
        labelWeight = SDGButtonFontWeight.SB,
        marginValues = PaddingValues(vertical = SDGSpacing.Spacing8)
    )
}

@Composable
private fun RowScope.SDGTwoOptionBottomPopupButton(
    startLabel: String,
    onClickStart: () -> Unit,
    endLabel: String,
    onClickEnd: () -> Unit,
) {
    SDGGhostButton(
        weight = 1f,
        size = SDGGhostButtonSize.Large,
        label = startLabel,
        labelColor = SDGColor.Neutral700,
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
        labelColor = SDGColor.Neutral700,
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
            onClickStart = {},
            endLabel = "Label",
            onClickEnd = {},
        )
    }
}
