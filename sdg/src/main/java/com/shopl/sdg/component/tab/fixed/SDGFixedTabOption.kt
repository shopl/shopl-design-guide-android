package com.shopl.sdg.component.tab.fixed

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

typealias Label = String

/** Fixed Tab의 Option 속성입니다. 각 옵션에 맞는 개수의 라벨을 전달합니다. */
@Immutable
sealed interface SDGFixedTabOption {
    val tabs: PersistentList<Label>

    data class TwoOption(
        override val tabs: PersistentList<Label>,
    ) : SDGFixedTabOption {
        constructor(tabs: List<Label>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 2) { "2 Option은 2개의 Fixed Tab 라벨로 구성해야 합니다." }
        }
    }

    data class ThreeOption(
        override val tabs: PersistentList<Label>,
    ) : SDGFixedTabOption {
        constructor(tabs: List<Label>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 3) { "3 Option은 3개의 Fixed Tab 라벨로 구성해야 합니다." }
        }
    }
}
