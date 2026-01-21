package com.shopl.sdg.component.button.capsule

import androidx.compose.ui.graphics.Color

sealed class SDGCapsuleButtonType {
    abstract val typeName: String

    data object Solid : SDGCapsuleButtonType() {
        override val typeName = "Solid"
    }

    data class Line(val lineColor: Color) : SDGCapsuleButtonType() {
        override val typeName = "Line"
    }
}
