package com.shopl.sdg.component.button.capsule

import androidx.compose.ui.graphics.Color

sealed class SDGCapsuleButtonType {
    data object Solid : SDGCapsuleButtonType()

    data class Line(val lineColor: Color) : SDGCapsuleButtonType()
}
