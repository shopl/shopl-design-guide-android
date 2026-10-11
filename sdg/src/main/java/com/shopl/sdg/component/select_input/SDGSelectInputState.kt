package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

@Stable
sealed interface SDGSelectInputState {
    val backgroundColor: Color?
    val typographyColor: Color
    val chevronColor: Color

    data object Default : SDGSelectInputState {
        override val backgroundColor: Color? = null
        override val typographyColor: Color = SDGColor.Neutral350
        override val chevronColor: Color = SDGColor.Neutral700
    }

    data object Selected : SDGSelectInputState {
        override val backgroundColor: Color? = null
        override val typographyColor: Color = SDGColor.Neutral700
        override val chevronColor: Color = SDGColor.Neutral700
    }

    data object Disabled : SDGSelectInputState {
        override val backgroundColor: Color? = null
        override val typographyColor: Color = SDGColor.Neutral700
        override val chevronColor: Color = SDGColor.Neutral300
    }

    data object Error : SDGSelectInputState {
        override val backgroundColor: Color? = SDGColor.Red300_a10
        override val typographyColor: Color = SDGColor.Neutral700
        override val chevronColor: Color = SDGColor.Neutral700
    }
}
