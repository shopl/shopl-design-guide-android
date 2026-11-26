package com.shopl.sdg.template.rich_text.toolbar.preview

import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarBackgroundColor
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarButtonState
import kotlinx.collections.immutable.PersistentMap

internal data class SDGRichTextToolbarPreviewParameter(
    val backgroundColor: SDGRichTextToolbarBackgroundColor = SDGRichTextToolbarBackgroundColor.LightGray,
    val buttons: PersistentMap<SDGRichTextToolbarAction, SDGRichTextToolbarButtonState> =
        SDGRichTextToolbarAction.defaultButtons,
    val showLinkInput: Boolean = false,
    val url: String = "",
    val showTopDivider: Boolean = true,
)
