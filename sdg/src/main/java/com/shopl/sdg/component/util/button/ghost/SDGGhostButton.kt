package com.shopl.sdg.component.util.button.ghost

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.SDGButtonFontWeight
import com.shopl.sdg.component.button.ghost.SDGGhostButton
import com.shopl.sdg.component.button.ghost.SDGGhostButtonSize

/**
 * [RowScope] [SDGGhostButton]
 */
@Composable
fun RowScope.SDGGhostButton(
    weight: Float,
    size: SDGGhostButtonSize,
    label: String,
    labelWeight: SDGButtonFontWeight = SDGButtonFontWeight.R,
    labelColor: Color,
    onClick: () -> Unit,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.weight(weight)) {
        SDGGhostButton(
            isFillMaxWidth = true,
            enable = enable,
            size = size,
            label = label,
            labelColor = labelColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
            labelWeight = labelWeight
        )
    }
}

/**
 * [BoxScope] [SDGGhostButton]
 */
@Composable
fun BoxScope.SDGGhostButton(
    alignment: Alignment,
    isFillMaxWidth: Boolean,
    size: SDGGhostButtonSize,
    label: String,
    labelWeight: SDGButtonFontWeight = SDGButtonFontWeight.R,
    labelColor: Color,
    onClick: () -> Unit,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.align(alignment)) {
        SDGGhostButton(
            isFillMaxWidth = isFillMaxWidth,
            enable = enable,
            size = size,
            label = label,
            labelColor = labelColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
            labelWeight = labelWeight
        )
    }
}


@Preview
@Composable
private fun PreviewRowScopeSDGGhostButton() {
    Row {
        SDGGhostButton(
            weight = 1f,
            labelWeight = SDGButtonFontWeight.R,
            size = SDGGhostButtonSize.Medium,
            label = "Ghost Button",
            labelColor = Color.Black,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewRowScopeSDGGhostButtonWithIcon() {
    Row {
        SDGGhostButton(
            weight = 1f,
            labelWeight = SDGButtonFontWeight.R,
            size = SDGGhostButtonSize.Medium,
            label = "Ghost Button",
            labelColor = Color.Black,
            onClick = {},
            leftIcon = android.R.drawable.ic_dialog_email
        )
    }
}

@Preview
@Composable
private fun PreviewBoxScopeSDGGhostButton() {
    Box {
        SDGGhostButton(
            alignment = Alignment.Center,
            isFillMaxWidth = true,
            size = SDGGhostButtonSize.Medium,
            label = "Ghost Button",
            labelColor = Color.Black,
            labelWeight = SDGButtonFontWeight.R,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewBoxScopeSDGGhostButtonWithIcon() {
    Box {
        SDGGhostButton(
            alignment = Alignment.Center,
            isFillMaxWidth = true,
            size = SDGGhostButtonSize.Medium,
            label = "Ghost Button",
            labelColor = Color.Black,
            onClick = {},
            labelWeight = SDGButtonFontWeight.SB,
            leftIcon = android.R.drawable.ic_dialog_email
        )
    }
}