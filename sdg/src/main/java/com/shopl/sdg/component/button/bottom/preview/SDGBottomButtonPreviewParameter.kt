package com.shopl.sdg.component.button.bottom.preview

import androidx.compose.foundation.layout.PaddingValues
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSize
import com.shopl.sdg.component.button.bottom.model.SDGBottomButtonType
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

internal data class SDGBottomButtonPreviewParameter(
    val label: String,
    val type: SDGBottomButtonType,
    val size: SDGBottomButtonSize,
    val enabled: Boolean,
    val marginValues: PaddingValues = PaddingValues(
        start = SDGSpacing.Spacing12,
        end = SDGSpacing.Spacing12,
        bottom = SDGSpacing.Spacing10,
    ),
)
