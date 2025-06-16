package com.shopl.sdg.component.button.box

import androidx.compose.ui.graphics.Color

sealed class SDGBoxButtonType {
    data object Solid : SDGBoxButtonType()

    data class Line(val lineColor: Color) : SDGBoxButtonType()
}