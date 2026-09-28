package com.shopl.sdg.template.history.custom.preview

import androidx.compose.foundation.layout.PaddingValues
import com.shopl.sdg.template.history.custom.SDGCustomHistoryPosition

internal data class SDGCustomHistoryPreviewParams(
    val position: SDGCustomHistoryPosition,
    val header: String,
    val body: String? = null,
    val bodyContentPadding: PaddingValues = PaddingValues(),
    val hasHeaderBackground: Boolean = true,
)
