package com.shopl.sdg.component.button.box

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
sealed class SDGBoxButtonType {
    abstract val typeName: String

    data object Solid : SDGBoxButtonType() {
        override val typeName = "Solid"
    }

    data class Line(val lineColor: Color) : SDGBoxButtonType() {
        override val typeName = "Line"
    }
}