package com.shopl.sdg.component.button.box

import androidx.compose.ui.graphics.Color

sealed class SDGBoxButtonType {
    abstract val typeName: String

    data object Solid : SDGBoxButtonType() {
        override val typeName = "Solid"
    }

    data class Line(val lineColor: Color) : SDGBoxButtonType() {
        override val typeName = "Line"
    }
}