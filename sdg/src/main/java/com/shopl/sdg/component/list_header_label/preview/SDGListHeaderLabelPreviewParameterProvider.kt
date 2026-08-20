package com.shopl.sdg.component.list_header_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelCount
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelShowDropdown

internal class SDGListHeaderLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGListHeaderLabelPreviewParams> {

    override val values: Sequence<SDGListHeaderLabelPreviewParams> = sequenceOf(
        라벨만(),
        라벨_카운트(),
        라벨_드롭다운(),
        라벨_카운트_드롭다운(),
        긴_라벨_카운트_드롭다운(),
    )

    private fun 라벨만() = SDGListHeaderLabelPreviewParams(
        label = "리스트 타이틀",
        count = SDGListHeaderLabelCount.False,
        showDropdown = SDGListHeaderLabelShowDropdown.False,
    )

    private fun 라벨_카운트() = SDGListHeaderLabelPreviewParams(
        label = "리스트 타이틀",
        count = SDGListHeaderLabelCount.True(countValue = "10"),
        showDropdown = SDGListHeaderLabelShowDropdown.False,
    )

    private fun 라벨_드롭다운() = SDGListHeaderLabelPreviewParams(
        label = "리스트 타이틀",
        count = SDGListHeaderLabelCount.False,
        showDropdown = SDGListHeaderLabelShowDropdown.True(onClick = {}),
    )

    private fun 라벨_카운트_드롭다운() = SDGListHeaderLabelPreviewParams(
        label = "리스트 타이틀",
        count = SDGListHeaderLabelCount.True(countValue = "99+"),
        showDropdown = SDGListHeaderLabelShowDropdown.True(onClick = {}),
    )

    private fun 긴_라벨_카운트_드롭다운() = SDGListHeaderLabelPreviewParams(
        label = "리스트 타이틀이 매우 길어서 화면을 넘어가는 경우에 대한 테스트입니다.",
        count = SDGListHeaderLabelCount.True(countValue = "99+"),
        showDropdown = SDGListHeaderLabelShowDropdown.True(onClick = {}),
    )
}
