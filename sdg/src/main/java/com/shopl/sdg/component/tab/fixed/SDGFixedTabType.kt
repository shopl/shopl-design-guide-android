package com.shopl.sdg.component.tab.fixed

import androidx.compose.runtime.Stable

@Stable
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