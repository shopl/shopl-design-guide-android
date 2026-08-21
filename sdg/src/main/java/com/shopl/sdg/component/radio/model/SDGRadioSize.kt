package com.shopl.sdg.component.radio.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class SDGRadioSize(
    val circleSize: Dp,
    val iconSize: Dp,
) {
    LARGE(
        circleSize = 18.dp,
        iconSize = 16.dp,
    ),
    MEDIUM(
        circleSize = 16.dp,
        iconSize = 14.dp,
    )
}
