package com.shopl.sdg.component.thumbnail.model

sealed interface SDGThumbnailsType {
    val onClick: (index: Int) -> Unit
    val thumbnails: List<SDGThumbnailUiModel>

    data class Photo(
        override val onClick: (index: Int) -> Unit,
        override val thumbnails: List<SDGThumbnailUiModel>,
    ) : SDGThumbnailsType

    data class Video(
        override val onClick: (index: Int) -> Unit,
        override val thumbnails: List<SDGThumbnailUiModel>,
    ) : SDGThumbnailsType
}
