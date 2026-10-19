package com.shopl.sdg.component.navigation.text

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

data class SDGTextNaviSubLabel(
    val text: String,
    override val onClick: () -> Unit,
    val textColor: Color = SDGColor.Neutral700,
) : SDGTextNaviSubItem
