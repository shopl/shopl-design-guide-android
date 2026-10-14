package com.shopl.sdg.component.navigation.category

data class SDGCategoryNaviLeftIconItem(
    val onClick: () -> Unit,
    val contentDescription: String? = null,
)
