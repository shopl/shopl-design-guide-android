package com.shopl.sdg.component.guide_container

import androidx.compose.ui.text.style.TextAlign

enum class SDGGuideTextAlignment(
    internal val textAlign: TextAlign,
) {
    LEFT(TextAlign.Start),
    RIGHT(TextAlign.End),
}
