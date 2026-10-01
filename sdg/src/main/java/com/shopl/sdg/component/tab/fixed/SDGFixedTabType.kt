package com.shopl.sdg.component.tab.fixed

import androidx.compose.runtime.Stable

@Stable
@Deprecated("SDGFixedTabOption을 사용하세요.")
sealed interface SDGFixedTabType {

    data class TwoOption(
        val firstTitle: String,
        val secondTitle: String,
    ) : SDGFixedTabType

    data class ThreeOption(
        val firstTitle: String,
        val secondTitle: String,
        val thirdTitle: String,
    ) : SDGFixedTabType

}
