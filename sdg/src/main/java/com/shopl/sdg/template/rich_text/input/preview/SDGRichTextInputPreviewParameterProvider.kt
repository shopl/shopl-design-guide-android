package com.shopl.sdg.template.rich_text.input.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGRichTextInputPreviewParameterProvider : PreviewParameterProvider<SDGRichTextInputPreviewParameter> {
    override val values = sequenceOf(기본(), 입력_중(), 입력_완료(), 비활성(), 글자_수_초과())

    private fun 기본() = SDGRichTextInputPreviewParameter()
    private fun 입력_중() = SDGRichTextInputPreviewParameter(html = "텍스트 입력", focused = true)
    private fun 입력_완료() = SDGRichTextInputPreviewParameter(html = "<h1>제목 1</h1><p>본문에 <strong>서식</strong>을 적용합니다.</p>")
    private fun 비활성() = SDGRichTextInputPreviewParameter(html = "텍스트 입력", enabled = false)
    private fun 글자_수_초과() = SDGRichTextInputPreviewParameter(html = "제한 글자 수를 초과한 본문입니다.", maxCount = 10)
}
