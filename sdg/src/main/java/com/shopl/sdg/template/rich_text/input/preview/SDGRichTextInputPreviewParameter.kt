package com.shopl.sdg.template.rich_text.input.preview

internal data class SDGRichTextInputPreviewParameter(
    val html: String = "",
    val maxCount: Int = 500,
    val enabled: Boolean = true,
    val focused: Boolean = false,
)
