package com.shopl.sdg.component.tab.fixed

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

typealias SDGFixedTabLabel = String

@Immutable
sealed interface SDGFixedTabOption {
    val tabs: PersistentList<SDGFixedTabLabel>

    data class TwoOption(
        override val tabs: PersistentList<SDGFixedTabLabel>,
    ) : SDGFixedTabOption {
        constructor(tabs: List<SDGFixedTabLabel>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 2) { "2 Option은 2개의 Fixed Tab 라벨로 구성해야 합니다." }
        }
    }

    data class ThreeOption(
        override val tabs: PersistentList<SDGFixedTabLabel>,
    ) : SDGFixedTabOption {
        constructor(tabs: List<SDGFixedTabLabel>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 3) { "3 Option은 3개의 Fixed Tab 라벨로 구성해야 합니다." }
        }
    }
}
