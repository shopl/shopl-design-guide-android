package com.shopl.sdg.component.util.button.capsule

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.button.capsule.SDGCapsuleButton
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonSize
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonType

/**
 * [RowScope] [SDGCapsuleButton]
 */
@Composable
fun RowScope.SDGCapsuleButton(
    weight: Float,
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconDownSized: Boolean = false,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.weight(weight)) {
        SDGCapsuleButton(
            isFillMaxWidth = true,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            iconDownSized = iconDownSized,
            onClick = onClick
        )
    }
}

/**
 * [BoxScope] [SDGCapsuleButton]
 */
@Composable
fun BoxScope.SDGCapsuleButton(
    alignment: Alignment,
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconDownSized: Boolean = false,
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.align(alignment)) {
        SDGCapsuleButton(
            isFillMaxWidth = isFillMaxWidth,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            iconDownSized = iconDownSized,
            backgroundColor = backgroundColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
        )
    }
}