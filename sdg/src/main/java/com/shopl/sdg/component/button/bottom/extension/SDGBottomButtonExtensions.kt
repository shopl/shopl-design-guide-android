package com.shopl.sdg.component.button.bottom.extension

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
import com.shopl.sdg.component.button.bottom.SDGBottomButton
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSpec
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

/**
 * [BoxScope] [SDGBottomButton]
 */
@Deprecated("BoxScope에서 size/type/label을 사용하는 신규 SDGBottomButton을 배치하세요.")
@Composable
@Suppress("DEPRECATION")
fun BoxScope.SDGBottomButton(
    align: Alignment,
    title: String,
    marginValues: PaddingValues = PaddingValues(),
    spec: SDGBottomButtonSpec = SDGBottomButtonSpec.FULL,
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(Modifier.align(align)) {
        SDGBottomButton(
            label = title,
            marginValues = marginValues,
            size = spec.toSize(),
            type = type.toType(),
            enabled = enabled,
            onClick = onClick,
        )
    }
}

/**
 * [RowScope] [SDGBottomButton]
 */
@Deprecated("RowScope에서 size/type/label을 사용하는 신규 SDGBottomButton을 배치하세요.")
@Composable
@Suppress("DEPRECATION")
fun RowScope.SDGBottomButton(
    weight: Float,
    title: String,
    marginValues: PaddingValues = PaddingValues(),
    size: SDGBottomButtonSpec = SDGBottomButtonSpec.FULL,
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(Modifier.weight(weight = weight, fill = true)) {
        SDGBottomButton(
            label = title,
            marginValues = marginValues,
            size = size.toSize(),
            type = type.toType(),
            enabled = enabled,
            onClick = onClick,
        )
    }
}

@Preview
@Composable
@Suppress("DEPRECATION")
private fun PreviewBottomButton() {
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
            ) {
                SDGBottomButton(
                    title = "NORMAL",
                    type = SDGBottomButtonType.NORMAL,
                    weight = 1f
                ) {}
                SDGBottomButton(
                    title = "POSITIVE",
                    type = SDGBottomButtonType.POSITIVE,
                    weight = 1f
                ) {}
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
            ) {
                SDGBottomButton(
                    title = "NEGATIVE",
                    type = SDGBottomButtonType.NEGATIVE,
                    weight = 1f
                ) {}
                SDGBottomButton(
                    title = "POSITIVE",
                    type = SDGBottomButtonType.POSITIVE,
                    weight = 1f
                ) {}
            }
        }
    }
}
