package com.shopl.sdg.template.history.custom.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import com.shopl.sdg.template.history.custom.SDGCustomHistoryPosition

internal data class SDGCustomHistoryPreviewParams(
    val position: SDGCustomHistoryPosition,
    val header: String,
    val body: String?,
    val headerModifier: Modifier,
    val bodyContentPadding: PaddingValues = PaddingValues(),
)
