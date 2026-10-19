package com.shopl.sdg.component.navigation.text.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.SDGTextNaviSubItem

internal data class SDGTextNaviPreviewParameter(
    val label: SDGTextNaviLabel,
    val backButton: SDGTextNaviBackButton,
    val subIcon: SDGTextNaviSubItem.Icon?,
    val subLabel: SDGTextNaviSubItem.Label?,
    val backgroundColor: Color,
)
