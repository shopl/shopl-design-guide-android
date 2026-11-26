package com.shopl.sdg.template.rich_text.view.preview

import com.shopl.sdg.template.rich_text.view.SDGRichTextViewState

internal data class SDGRichTextViewPreviewParameter(
    val text: String,
    val placeholder: String,
    val state: SDGRichTextViewState,
)
