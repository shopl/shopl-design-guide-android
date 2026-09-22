package com.shopl.sdg.component.tab.box

sealed interface SDGBoxTabItemState {
    data object Selected : SDGBoxTabItemState
    data object Unselected : SDGBoxTabItemState
}