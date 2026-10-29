package com.shopl.sdg.component.button.bottom.preview

import androidx.compose.foundation.layout.PaddingValues
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSpec
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

internal data class SDGBottomButtonPreviewParameter(
    val title: String,
    val type: SDGBottomButtonType,
    val spec: SDGBottomButtonSpec,
    val enabled: Boolean,
    val marginValues: PaddingValues = PaddingValues(
        start = SDGSpacing.Spacing12,
        end = SDGSpacing.Spacing12,
        bottom = SDGSpacing.Spacing10,
    ),
)
