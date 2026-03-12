package com.shopl.sdg.component.list_header_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGListHeaderLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGListHeaderLabelPreviewParams> {

    override val values: Sequence<SDGListHeaderLabelPreviewParams> = sequenceOf(
        타이틀만(),
        타이틀_카운트(),
        타이틀_드롭다운아이콘(),
        타이틀_카운트_드롭다운아이콘(),
        긴타이틀_카운트_드롭다운아이콘(),
    )

    private fun 타이틀만() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = null,
        dropdownIcon = false
    )

    private fun 타이틀_카운트() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = "10",
        dropdownIcon = false
    )

    private fun 타이틀_드롭다운아이콘() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = null,
        dropdownIcon = true
    )

    private fun 타이틀_카운트_드롭다운아이콘() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = "99+",
        dropdownIcon = true
    )

    private fun 긴타이틀_카운트_드롭다운아이콘() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀이 매우 길어서 화면을 넘어가는 경우에 대한 테스트입니다.",
        count = "99+",
        dropdownIcon = true
    )
}
