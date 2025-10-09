package com.shopl.sdg.component.dropdown

import androidx.compose.runtime.Stable

@Stable
sealed interface DropdownState {
    data object Default : DropdownState
    data object Error : DropdownState
}