package com.shopl.sdg.component.navigation.text

data class SDGTextNaviRightItem(
    val subLabel: SDGTextNaviSubLabel,
    val subIcon: SDGTextNaviSubIcon,
    val onClick: () -> Unit,
)
