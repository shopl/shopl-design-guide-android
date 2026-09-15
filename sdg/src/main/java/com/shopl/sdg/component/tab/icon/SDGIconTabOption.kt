package com.shopl.sdg.component.tab.icon

/** Icon Tab의 Option 속성입니다. 각 옵션에 맞는 개수의 탭을 전달합니다. */
sealed interface SDGIconTabOption {
    val tabs: List<SDGTabItem>

    data class ThreeOption(
        override val tabs: List<SDGTabItem>,
    ) : SDGIconTabOption {
        init {
            require(tabs.size == 3) { "3 Option은 3개의 Icon Tab Item으로 구성해야 합니다." }
        }
    }

    data class FourOption(
        override val tabs: List<SDGTabItem>,
    ) : SDGIconTabOption {
        init {
            require(tabs.size == 4) { "4 Option은 4개의 Icon Tab Item으로 구성해야 합니다." }
        }
    }

    data class FiveOption(
        override val tabs: List<SDGTabItem>,
    ) : SDGIconTabOption {
        init {
            require(tabs.size == 5) { "5 Option은 5개의 Icon Tab Item으로 구성해야 합니다." }
        }
    }
}
