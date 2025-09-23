package com.shopl.sdg.component.time_picker

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed interface SDGTimePickerOption {
    data class OneOption(
        val value: Int,
        val range: IntRange,
        val onValueChange: (Int) -> Unit,
        val width: Dp = 0.dp,
    ) : SDGTimePickerOption

    data class TwoOption(
        val left: OptionModel,
        val right: OptionModel,
    ) : SDGTimePickerOption {
        data class OptionModel(
            val value: Int,
            val range: IntRange,
            val onValueChange: (Int) -> Unit,
            val width: Dp = 0.dp,
        )
    }
}
