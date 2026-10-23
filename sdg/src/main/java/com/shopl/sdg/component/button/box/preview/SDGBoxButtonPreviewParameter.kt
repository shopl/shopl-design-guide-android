package com.shopl.sdg.component.button.box.preview

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.component.button.box.SDGBoxButtonType

internal data class SDGBoxButtonPreviewParameter(
    val size: SDGBoxButtonSize,
    val type: SDGBoxButtonType,
    val label: String,
    val labelColor: Color,
    val backgroundColor: Color,
    val iconDownSize: Boolean = false,
    val enable: Boolean = true,
    val isFillMaxWidth: Boolean = false,
    @DrawableRes val leftIcon: Int? = null,
    val leftIconTint: Color? = null,
    @DrawableRes val rightIcon: Int? = null,
    val rightIconTint: Color? = null,
    val maxWidth: Dp? = null,
    val marginValues: PaddingValues = PaddingValues(0.dp),
)
