package com.shopl.sdg.component.tab.icon

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

/**
 * Icon Tab의 Option 속성입니다. 각 옵션에 맞는 개수의 탭을 전달합니다.
 * 전달받은 List는 불변 목록으로 저장하므로 원본을 수정해도 옵션의 탭은 변경되지 않습니다.
 */
sealed interface SDGIconTabOption {
    val tabs: List<SDGTabItem>

    data class ThreeOption(
        override val tabs: PersistentList<SDGTabItem>,
    ) : SDGIconTabOption {
        constructor(tabs: List<SDGTabItem>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 3) { "3 Option은 3개의 Icon Tab Item으로 구성해야 합니다." }
        }

        fun copy(tabs: List<SDGTabItem>): ThreeOption = ThreeOption(tabs = tabs)
    }

    data class FourOption(
        override val tabs: PersistentList<SDGTabItem>,
    ) : SDGIconTabOption {
        constructor(tabs: List<SDGTabItem>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 4) { "4 Option은 4개의 Icon Tab Item으로 구성해야 합니다." }
        }

        fun copy(tabs: List<SDGTabItem>): FourOption = FourOption(tabs = tabs)
    }

    data class FiveOption(
        override val tabs: PersistentList<SDGTabItem>,
    ) : SDGIconTabOption {
        constructor(tabs: List<SDGTabItem>) : this(tabs.toPersistentList())

        init {
            require(tabs.size == 5) { "5 Option은 5개의 Icon Tab Item으로 구성해야 합니다." }
        }

        fun copy(tabs: List<SDGTabItem>): FiveOption = FiveOption(tabs = tabs)
    }
}
