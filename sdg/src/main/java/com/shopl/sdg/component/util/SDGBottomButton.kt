package com.shopl.sdg.component.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.bottom.SDGBottomButton
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType

/**
 * [BoxScope] [SDGBottomButton]
 */
@Composable
fun BoxScope.SDGBottomButton(
    align: Alignment,
    title: String,
    marginValues: PaddingValues = PaddingValues(),
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(Modifier.align(align)) {
        SDGBottomButton(
            title = title,
            marginValues = marginValues,
            type = type,
            enabled = enabled,
            onClick = onClick,
        )
    }
}

/**
 * [RowScope] [SDGBottomButton]
 */
@Composable
fun RowScope.SDGBottomButton(
    weight: Float,
    title: String,
    marginValues: PaddingValues = PaddingValues(),
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(Modifier.weight(weight)) {
        SDGBottomButton(
            title = title,
            marginValues = marginValues,
            type = type,
            enabled = enabled,
            onClick = onClick,
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SDGBottomButton(
                    title = "NORMAL",
                    type = SDGBottomButtonType.NORMAL,
                    weight = 0.5F
                ) {}
                SDGBottomButton(
                    title = "POSITIVE",
                    type = SDGBottomButtonType.POSITIVE,
                    weight = 0.5F
                ) {}
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SDGBottomButton(
                    title = "NEGATIVE",
                    type = SDGBottomButtonType.NEGATIVE,
                    weight = 0.5F
                ) {}
                SDGBottomButton(
                    title = "POSITIVE",
                    type = SDGBottomButtonType.POSITIVE,
                    weight = 0.5F
                ) {}
            }
        }
    }
}
