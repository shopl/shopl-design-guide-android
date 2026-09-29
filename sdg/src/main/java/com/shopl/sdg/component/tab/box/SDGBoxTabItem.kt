package com.shopl.sdg.component.tab.box

data class SDGBoxTabItem(
    val state: SDGBoxTabItemState,
    val label: String,
    val showTwoDepth: ShowTwoDepth,
) {
    sealed interface ShowTwoDepth {
        data class True(
            val text: String,
            val showCount: ShowCount
        ) : ShowTwoDepth {

            sealed interface ShowCount {
                data class True(
                    val count: String
                ) : ShowCount

                data object False : ShowCount
            }

        }

        data object False : ShowTwoDepth
    }
}