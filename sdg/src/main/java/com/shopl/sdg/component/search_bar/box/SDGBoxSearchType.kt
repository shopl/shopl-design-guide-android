package com.shopl.sdg.component.search_bar.box

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

@Stable
sealed interface SDGBoxSearchType {
    data class Solid(
        val backgroundColor: SolidColor = SolidColor.Neutral0,
    ) : SDGBoxSearchType {
        enum class SolidColor(val value: Color) {
            Neutral0(SDGColor.Neutral0),
            Neutral50(SDGColor.Neutral50),
        }

    }

    data object Line : SDGBoxSearchType
}
