package com.shopl.sdg.component.thumbnail.model

sealed interface SDGThumbnailsMode {
    data object Single : SDGThumbnailsMode

    data class Multi(
        val showClearIcon: SDGThumbnailsShowClearIcon,
    ) : SDGThumbnailsMode
}
