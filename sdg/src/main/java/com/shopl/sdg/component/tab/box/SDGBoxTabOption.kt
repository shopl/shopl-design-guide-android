package com.shopl.sdg.component.tab.box

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

sealed interface SDGBoxTabOption {
    val tabs: List<SDGBoxTabItem>

    data class TwoOption(
        override val tabs: PersistentList<SDGBoxTabItem>
    ) : SDGBoxTabOption {
        constructor(tabs: List<SDGBoxTabItem>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 2) { "2 Option은 2개의 Box Tab Item으로 구성해야 합니다." }
        }
    }

    data class ThreeOption(
        override val tabs: PersistentList<SDGBoxTabItem>
    ) : SDGBoxTabOption {
        constructor(tabs: List<SDGBoxTabItem>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 3) { "3 Option은 3개의 Box Tab Item으로 구성해야 합니다." }
        }
    }
}
