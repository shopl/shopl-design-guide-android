package com.shopl.sdg.component.toggle.style

import com.shopl.sdg_common.foundation.SDGColor

enum class SDGToggleStyle(
    internal val colors: SDGToggleColors,
) {
    PRIMARY(
        colors = SDGToggleColors(
            onTrackColor = SDGColor.Primary300,
            onThumbColor = SDGColor.Neutral0,
            offTrackColor = SDGColor.Neutral300,
            offThumbColor = SDGColor.Neutral0,
            disabledOnTrackColor = SDGColor.Neutral200,
            disabledOnThumbColor = SDGColor.Neutral0,
            disabledOffTrackColor = SDGColor.Neutral200,
            disabledOffThumbColor = SDGColor.Neutral0,
        )
    ),
    NEUTRAL(
        colors = SDGToggleColors(
            onTrackColor = SDGColor.Neutral700,
            onThumbColor = SDGColor.Neutral0,
            offTrackColor = SDGColor.Neutral300,
            offThumbColor = SDGColor.Neutral0,
            disabledOnTrackColor = SDGColor.Neutral200,
            disabledOnThumbColor = SDGColor.Neutral0,
            disabledOffTrackColor = SDGColor.Neutral200,
            disabledOffThumbColor = SDGColor.Neutral0,
        )
    )
}