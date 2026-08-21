package com.shopl.sdg.component.thumbnail.model

sealed interface SDGThumbnailsShowClearIcon {
    data class True(val onClick: (index: Int) -> Unit) : SDGThumbnailsShowClearIcon
    data object False : SDGThumbnailsShowClearIcon
}
