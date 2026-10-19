package com.shopl.sdg.component.navigation.text

sealed interface SDGTextNaviSubItem {
    val onClick: () -> Unit
}
