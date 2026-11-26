package com.shopl.sdg.template.rich_text.view.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGRichTextViewPreviewParameterProvider :
    PreviewParameterProvider<SDGRichTextViewPreviewParameter> {
    override val values: Sequence<SDGRichTextViewPreviewParameter> =
        sequenceOf(일반_본문(), 서식_본문())

    private fun 일반_본문() = SDGRichTextViewPreviewParameter(text = "<p>한 줄 본문입니다.</p>")

    private fun 서식_본문() = SDGRichTextViewPreviewParameter(
        text = """
            <h1>제목 1</h1>
            <p>기본, <strong>Bold</strong>, <u>밑줄</u>, <s>취소선</s>, <a href="https://www.shoplworks.com">링크</a></p>
            <ul><li>글머리 기호 목록</li></ul>
            <ol><li>번호 목록</li></ol>
            <h2>제목 2</h2>
            <h3>제목 3</h3>
        """.trimIndent(),
    )
}
