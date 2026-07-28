package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Stable

/**
 * [SDGSelectInput]의 상태입니다.
 */
@Stable
sealed interface SDGSelectInputState {
    data object Default : SDGSelectInputState
    data object Selected : SDGSelectInputState
    data object Disabled : SDGSelectInputState
    data object Error : SDGSelectInputState
}
