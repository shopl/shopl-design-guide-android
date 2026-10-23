package com.shopl.sdg.component.empty_icon.preview

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues

internal data class SDGEmptyIconPreviewParameter(
    @DrawableRes val iconResId: Int,
    val label: String,
    val marginValues: PaddingValues,
)
