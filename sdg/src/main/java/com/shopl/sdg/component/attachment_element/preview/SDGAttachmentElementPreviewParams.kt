package com.shopl.sdg.component.attachment_element.preview

import com.shopl.sdg.component.attachment_element.SDGAttachmentElementState
import com.shopl.sdg.component.attachment_element.SDGAttachmentElementType

internal data class SDGAttachmentElementPreviewParams(
    val fileName: String,
    val fileSize: String,
    val state: SDGAttachmentElementState,
    val type: SDGAttachmentElementType,
)
