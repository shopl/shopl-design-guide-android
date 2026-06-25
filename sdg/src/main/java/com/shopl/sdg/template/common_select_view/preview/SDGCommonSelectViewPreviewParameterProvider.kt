package com.shopl.sdg.template.common_select_view.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.common_select_view.SDGCommonSelectViewState
import kotlinx.collections.immutable.persistentListOf

internal class SDGCommonSelectViewPreviewParameterProvider :
    PreviewParameterProvider<SDGCommonSelectViewPreviewParams> {

    override val values: Sequence<SDGCommonSelectViewPreviewParams> = sequenceOf(
        기본_상태(),
        검색어_입력_상태(),
        빈_데이터_상태(),
    )

    private fun 기본_상태() = SDGCommonSelectViewPreviewParams(
        title = "제목",
        searchInput = "",
        state = SDGCommonSelectViewState.Default,
        items = persistentListOf(
            "선택 항목 1",
            "선택 항목 2",
            "선택 항목 3",
        ),
    )

    private fun 검색어_입력_상태() = SDGCommonSelectViewPreviewParams(
        title = "제목",
        searchInput = "검색어",
        state = SDGCommonSelectViewState.Default,
        items = persistentListOf(
            "검색 결과 1",
            "검색 결과 2",
            "검색 결과 3",
        ),
    )

    private fun 빈_데이터_상태() = SDGCommonSelectViewPreviewParams(
        title = "제목",
        searchInput = "",
        state = SDGCommonSelectViewState.NoResult(
            description = "결과가 없습니다."
        ),
        items = persistentListOf(),
    )
}
