package com.shopl.sdg.component.toggle.spec

import androidx.compose.ui.unit.dp

enum class SDGToggleSpec(
    internal val size: SDGToggleSize,
) {
    MEDIUM(
        size = SDGToggleSize(
            width = 40.dp,
            height = 22.dp,
            trackRadius = 100.dp,
            thumbGap = 2.dp
        )
    ),
    SMALL(
        size = SDGToggleSize(
            width = 28.dp,
            height = 16.dp,
            trackRadius = 70.dp,
            thumbGap = 1.5.dp
        )
    ),
}