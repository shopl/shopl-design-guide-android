package com.shopl.sdg.component.navigation.category

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList

internal data class SDGCategoryNaviPreviewParameter(
    val label: SDGCategoryNaviLabel,
    val leftIcon: SDGCategoryNaviLeftIconItem?,
    val rightIcons: PersistentList<SDGCategoryNaviIconItem>?,
    val backgroundColor: Color = SDGColor.Neutral100,
    val hasDropdown: SDGCategoryNaviHasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
)
