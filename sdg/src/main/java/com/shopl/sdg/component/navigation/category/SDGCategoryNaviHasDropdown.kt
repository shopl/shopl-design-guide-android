package com.shopl.sdg.component.navigation.category

sealed interface SDGCategoryNaviHasDropdown {
    data class True(val onClick: () -> Unit) : SDGCategoryNaviHasDropdown
    data object False : SDGCategoryNaviHasDropdown
}
