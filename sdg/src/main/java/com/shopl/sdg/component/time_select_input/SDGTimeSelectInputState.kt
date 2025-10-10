package com.shopl.sdg.component.time_select_input

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGTimeSelectInputState {
    data object Default : SDGTimeSelectInputState
    data object Disabled : SDGTimeSelectInputState
    data object Error : SDGTimeSelectInputState
}