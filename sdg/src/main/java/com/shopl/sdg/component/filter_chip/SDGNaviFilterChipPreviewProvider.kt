package com.shopl.sdg.component.filter_chip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

internal class SDGNaviFilterChipPreviewParameterProvider :
    PreviewParameterProvider<SDGNaviFilterChipPreviewParams> {
    override val values: Sequence<SDGNaviFilterChipPreviewParams> = sequenceOf(
        배경이_Neutral100_경우_WHITE_칩을_사용합니다(),
        배경이_Neutral0_경우_LIGHT_GRAY_칩을_사용합니다(),
        라벨이_16글자를_초과할_경우_말줄임표가_노출됩니다(),
        칩의_필터_갯수가_1개인_경우_수량이_노출되지_않습니다()
    )

    private fun 배경이_Neutral100_경우_WHITE_칩을_사용합니다() = SDGNaviFilterChipPreviewParams(
        filters = List(5) {
            SDGNaviFilterItem(label = "label", count = it)
        }.toPersistentList(),
        chipBackgroundColor = SDGNaviFilterChipColor.WHITE,
        backgroundColor = SDGColor.Neutral100,
    )

    private fun 배경이_Neutral0_경우_LIGHT_GRAY_칩을_사용합니다() = SDGNaviFilterChipPreviewParams(
        filters = List(5) {
            SDGNaviFilterItem(label = "label", count = it)
        }.toPersistentList(),
        chipBackgroundColor = SDGNaviFilterChipColor.LIGHT_GRAY,
        backgroundColor = SDGColor.Neutral0,
    )

    private fun 라벨이_16글자를_초과할_경우_말줄임표가_노출됩니다() = SDGNaviFilterChipPreviewParams(
        filters = List(3) {
            SDGNaviFilterItem(label = "이것은16글자를초과하는긴텍스트입니다", count = it)
        }.toPersistentList()
    )

    private fun 칩의_필터_갯수가_1개인_경우_수량이_노출되지_않습니다() = SDGNaviFilterChipPreviewParams(
        filters = persistentListOf(
            SDGNaviFilterItem(label = "label", count = 1)
        )
    )
}

internal data class SDGNaviFilterChipPreviewParams(
    val filters: PersistentList<SDGNaviFilterItem>,
    val backgroundColor: Color = SDGColor.Neutral100,
    val chipBackgroundColor: SDGNaviFilterChipColor = SDGNaviFilterChipColor.WHITE,
)
