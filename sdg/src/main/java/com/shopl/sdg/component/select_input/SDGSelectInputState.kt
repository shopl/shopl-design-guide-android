package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGSelectInputState {
    data object Default : SDGSelectInputState
    data object Disabled : SDGSelectInputState
    data object Error : SDGSelectInputState
}