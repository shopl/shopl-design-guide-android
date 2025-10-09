package com.shopl.sdg.component.dropdown

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGDropdownState {
    data object Default : SDGDropdownState
    data object Error : SDGDropdownState
}