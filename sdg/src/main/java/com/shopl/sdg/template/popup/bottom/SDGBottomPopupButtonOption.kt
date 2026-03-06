package com.shopl.sdg.template.popup.bottom

import androidx.compose.runtime.Stable

@Stable
sealed interface SDGBottomPopupButtonOption {
    data class OneOption(
        val label: String,
        val onClick: () -> Unit,
    ) : SDGBottomPopupButtonOption

    data class TwoOption(
        val startLabel: String,
        val endLabel: String,
        val onClickStart: () -> Unit,
        val onClickEnd: () -> Unit,
    ) : SDGBottomPopupButtonOption
}
