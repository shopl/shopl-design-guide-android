package com.shopl.sdg.component.button.bottom.preview

import androidx.compose.foundation.layout.PaddingValues
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSize
import com.shopl.sdg.component.button.bottom.SDGBottomButtonStyle
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

internal data class SDGBottomButtonPreviewParameter(
    val label: String,
    val style: SDGBottomButtonStyle,
    val size: SDGBottomButtonSize,
    val enabled: Boolean,
    val marginValues: PaddingValues = PaddingValues(
        start = SDGSpacing.Spacing12,
        end = SDGSpacing.Spacing12,
        bottom = SDGSpacing.Spacing10,
    ),
)
