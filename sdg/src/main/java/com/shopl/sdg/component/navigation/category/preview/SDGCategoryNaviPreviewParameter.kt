package com.shopl.sdg.component.navigation.category.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviHasDropdown
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviIconItem
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviLabel
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviLeftIconItem
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList

internal data class SDGCategoryNaviPreviewParameter(
    val label: SDGCategoryNaviLabel,
    val leftIcon: SDGCategoryNaviLeftIconItem?,
    val rightIcons: PersistentList<SDGCategoryNaviIconItem>?,
    val backgroundColor: Color = SDGColor.Neutral100,
    val hasDropdown: SDGCategoryNaviHasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
)
