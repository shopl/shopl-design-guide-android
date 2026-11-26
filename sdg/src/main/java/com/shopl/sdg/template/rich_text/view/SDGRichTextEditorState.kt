package com.shopl.sdg.template.rich_text.view

import androidx.annotation.RestrictTo

/** 편집기가 전달하는 HTML 본문, 순수 텍스트 및 실제 포커스 상태입니다. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
data class SDGRichTextEditorState(
    val html: String,
    val plainText: String,
    val isEmpty: Boolean,
    val hasFocus: Boolean,
)
