package com.shopl.sdg.template.rich_text.view

import androidx.annotation.RestrictTo
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/** Rich Text View의 표시 상태입니다. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
enum class SDGRichTextViewState(
    internal val textColor: Color,
    internal val showsPlaceholder: Boolean = false,
    internal val isEditable: Boolean = true,
) {
    Default(SDGColor.Neutral700, showsPlaceholder = true),
    Focused(SDGColor.Neutral700),
    Completed(SDGColor.Neutral700),
    Disabled(SDGColor.Neutral300, isEditable = false),
}
