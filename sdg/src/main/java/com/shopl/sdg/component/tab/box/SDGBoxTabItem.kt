package com.shopl.sdg.component.tab.box

data class SDGBoxTabItem(
    val state: SDGBoxTabItemState,
    val label: String,
    val showTwoDepth: ShowTwoDepth,
) {
    sealed interface ShowTwoDepth {
        data class True(
            val text: String,
            val count:String?
        ) : ShowTwoDepth

        data object False : ShowTwoDepth
    }
}