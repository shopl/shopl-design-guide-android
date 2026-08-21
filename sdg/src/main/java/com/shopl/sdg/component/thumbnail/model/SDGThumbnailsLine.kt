package com.shopl.sdg.component.thumbnail.model

sealed interface SDGThumbnailsLine {
    data object SingleLine : SDGThumbnailsLine
    data class MultiLine(
        val showClearIcon: SDGThumbnailsShowClearIcon,
    ) : SDGThumbnailsLine
}
