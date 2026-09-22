package com.shopl.sdg.component.tab.box

/**
 * 
 */
data class SDGBoxTabItem(
    val state: SDGBoxTabItemState,
    val label: String,
    val showTwoDepth: Boolean,
    val text: String?,
    val count: String?,
    val showCount: Boolean
)