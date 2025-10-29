package com.shopl.sdg.component.number_picker

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList

sealed interface SDGNumberPickerOption {
    data class OneOption(
        val value: Int,
        val rangeList: PersistentList<Int>,
        val onValueChange: (Int) -> Unit,
        val width: Dp = 0.dp,
        val supportsInfiniteScroll: Boolean,
    ) : SDGNumberPickerOption

    data class TwoOption(
        val left: OptionModel,
        val right: OptionModel,
    ) : SDGNumberPickerOption {
        data class OptionModel(
            val value: Int,
            val rangeList: PersistentList<Int>,
            val onValueChange: (Int) -> Unit,
            val width: Dp = 0.dp,
            val supportsInfiniteScroll: Boolean,
        )
    }
}
