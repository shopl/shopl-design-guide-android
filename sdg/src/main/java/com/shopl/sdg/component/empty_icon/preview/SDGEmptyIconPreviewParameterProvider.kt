package com.shopl.sdg.component.empty_icon.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R

internal class SDGEmptyIconPreviewParameterProvider : PreviewParameterProvider<SDGEmptyIconPreviewParameter> {
    override val values = sequenceOf(
        검색_결과_없음(),
        등록된_구성원_없음(),
        긴_라벨(),
        여러_줄_라벨(),
    )

    private fun 검색_결과_없음() = SDGEmptyIconPreviewParameter(
        iconResId = R.drawable.ic_empty_data,
        label = "결과가 없습니다.",
        marginValues = PaddingValues(horizontal = SDGSpacing.Spacing28),
    )

    private fun 등록된_구성원_없음() = SDGEmptyIconPreviewParameter(
        iconResId = R.drawable.empty_member,
        label = "등록된 구성원이 없습니다.",
        marginValues = PaddingValues(
            start = SDGSpacing.Spacing28,
            top = SDGSpacing.Spacing60,
            end = SDGSpacing.Spacing28,
        ),
    )

    private fun 긴_라벨() = SDGEmptyIconPreviewParameter(
        iconResId = R.drawable.ic_empty_data,
        label = "검색 결과가 없습니다. 검색어를 확인한 후 다시 검색해 주세요.",
        marginValues = PaddingValues(horizontal = SDGSpacing.Spacing28),
    )

    private fun 여러_줄_라벨() = SDGEmptyIconPreviewParameter(
        iconResId = R.drawable.ic_empty_data,
        label = "데이터를 불러오지 못했습니다.\n잠시 후 다시 시도해 주세요.",
        marginValues = PaddingValues(horizontal = SDGSpacing.Spacing28),
    )
}
