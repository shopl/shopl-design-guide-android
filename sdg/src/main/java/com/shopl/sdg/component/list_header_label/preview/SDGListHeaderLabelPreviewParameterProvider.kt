package com.shopl.sdg.component.list_header_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGListHeaderLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGListHeaderLabelPreviewParams> {

    override val values: Sequence<SDGListHeaderLabelPreviewParams> = sequenceOf(
        Title_Only(),
        Title_With_Count(),
        Title_With_Icon(),
        Title_With_Count_And_Icon(),
        Long_Title_With_Count_And_Icon(),
    )

    private fun Title_Only() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = null,
        dropdownIcon = false
    )

    private fun Title_With_Count() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = "10",
        dropdownIcon = false
    )

    private fun Title_With_Icon() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = null,
        dropdownIcon = true
    )

    private fun Title_With_Count_And_Icon() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀",
        count = "99+",
        dropdownIcon = true
    )

    private fun Long_Title_With_Count_And_Icon() = SDGListHeaderLabelPreviewParams(
        title = "리스트 타이틀이 매우 길어서 화면을 넘어가는 경우에 대한 테스트입니다.",
        count = "99+",
        dropdownIcon = true
    )
}
