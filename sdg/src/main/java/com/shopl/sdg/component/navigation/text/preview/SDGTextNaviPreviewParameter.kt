package com.shopl.sdg.component.navigation.text.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.navigation.text.SDGTextNaviActionItem
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.SDGTextNaviShowActionIc

internal data class SDGTextNaviPreviewParameter(
    val label: SDGTextNaviLabel,
    val backButton: SDGTextNaviBackButton,
    val showActionIc: SDGTextNaviShowActionIc,
    val actionLabel: SDGTextNaviActionItem.Label,
    val backgroundColor: Color,
)
