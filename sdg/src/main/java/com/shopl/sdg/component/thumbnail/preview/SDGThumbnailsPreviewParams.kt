package com.shopl.sdg.component.thumbnail.preview

import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsLine
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsType

internal data class SDGThumbnailsPreviewParams(
    val type: SDGThumbnailsType,
    val line: SDGThumbnailsLine,
)
