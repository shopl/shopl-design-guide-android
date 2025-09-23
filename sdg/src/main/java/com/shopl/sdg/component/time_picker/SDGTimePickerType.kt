package com.shopl.sdg.component.time_picker

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val DEFAULT_TIME_PICKER_TWO_OPTION_SPACING = 4.dp
internal val DEFAULT_TIME_PICKER_DIVIDER_HORIZONTAL_PADDING = 8.dp

sealed interface SDGTimePickerOption {
    data class OneOption(
        val value: Int,
        val range: IntRange,
        val onValueChange: (Int) -> Unit,
        val width: Dp = 0.dp,
        val isEditMode: Boolean = false,
    ) : SDGTimePickerOption

    data class TwoOption(
        val first: SDGTimePickerColumnOption,
        val second: SDGTimePickerColumnOption,
        val dividerText: String = ":",
        val spacing: Dp = DEFAULT_TIME_PICKER_TWO_OPTION_SPACING,
        val dividerHorizontalPadding: Dp = DEFAULT_TIME_PICKER_DIVIDER_HORIZONTAL_PADDING,
    ) : SDGTimePickerOption
}

data class SDGTimePickerColumnOption(
    val value: Int,
    val range: IntRange,
    val onValueChange: (Int) -> Unit,
    val width: Dp = 0.dp,
    val isEditMode: Boolean = false,
)
