package com.shopl.sdg.template.rich_text.view.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.rich_text.view.SDGRichTextViewState

internal class SDGRichTextViewPreviewParameterProvider :
    PreviewParameterProvider<SDGRichTextViewPreviewParameter> {
    override val values: Sequence<SDGRichTextViewPreviewParameter> =
        sequenceOf(기본(), 포커스(), 완료(), 비활성())

    private fun 기본() = SDGRichTextViewPreviewParameter(
        text = "",
        placeholder = "입력",
        state = SDGRichTextViewState.Default,
    )

    private fun 포커스() = SDGRichTextViewPreviewParameter(
        text = "<p>텍스트 입력</p>",
        placeholder = "입력",
        state = SDGRichTextViewState.Focused,
    )

    private fun 완료() = SDGRichTextViewPreviewParameter(
        text = """
            <h1>제목 1</h1>
            <p>기본, <strong>Bold</strong>, <u>밑줄</u>, <s>취소선</s>, <a href="https://www.shoplworks.com">링크</a></p>
            <ul><li>글머리 기호 목록</li></ul>
            <ol><li>번호 목록</li></ol>
            <h2>제목 2</h2>
            <h3>제목 3</h3>
        """.trimIndent(),
        placeholder = "입력",
        state = SDGRichTextViewState.Completed,
    )

    private fun 비활성() = SDGRichTextViewPreviewParameter(
        text = "<p>텍스트 입력</p>",
        placeholder = "입력",
        state = SDGRichTextViewState.Disabled,
    )
}
