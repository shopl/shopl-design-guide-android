package com.shopl.sdg.component.navigation.text.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.navigation.text.SDGTextNaviActionItem
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.ShowActionIcon

internal data class SDGTextNaviPreviewParameter(
    val label: SDGTextNaviLabel,
    val backButton: SDGTextNaviBackButton,
    val showActionIcon: ShowActionIcon,
    val actionLabel: SDGTextNaviActionItem.Label,
    val backgroundColor: Color,
)
