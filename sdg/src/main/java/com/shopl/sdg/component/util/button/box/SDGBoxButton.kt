package com.shopl.sdg.component.util.button.box

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.box.SDGBoxButton
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType
import com.shopl.sdg_common.foundation.SDGColor

/**
 * [RowScope] [SDGBoxButton]
 */
@Composable
fun RowScope.SDGBoxButton(
    weight: Float,
    enable: Boolean = true,
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconDownSize: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.weight(weight)) {
        SDGBoxButton(
            isFillMaxWidth = true,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            iconDownSize = iconDownSize,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
        )
    }
}

/**
 * [BoxScope] [SDGBoxButton]
 */
@Composable
fun BoxScope.SDGBoxButton(
    alignment: Alignment,
    isFillMaxWidth: Boolean,
    enable: Boolean = true,
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconDownSize: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.align(alignment)) {
        SDGBoxButton(
            isFillMaxWidth = isFillMaxWidth,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            iconDownSize = iconDownSize,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRowScopeSDGBoxButton() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        SDGBoxButton(
            weight = 1f,
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Solid,
            label = "RowScope Button",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Primary300,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBoxScopeSDGBoxButton() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        SDGBoxButton(
            alignment = Alignment.Center,
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Line(SDGColor.Primary300),
            label = "BoxScope Button",
            labelColor = SDGColor.Neutral700,
            backgroundColor = Color.Transparent,
            isFillMaxWidth = false,
            onClick = { }
        )
    }
}