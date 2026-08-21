package com.shopl.sdg.component.thumbnail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailUiModel
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsLine
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsShowClearIcon
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsType

internal class SDGThumbnailsExtensionPreviewParameterProvider :
    PreviewParameterProvider<SDGThumbnailsPreviewParams> {

    override val values: Sequence<SDGThumbnailsPreviewParams> = sequenceOf(
        사진_싱글(),
        사진_멀티(),
    )

    private fun 사진_싱글() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Photo(
            onClick = {},
            thumbnails = List(
                size = 13,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "photo_single_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.SingleLine,
    )

    private fun 사진_멀티() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Photo(
            onClick = {},
            thumbnails = List(
                size = 10,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "photo_multi_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.MultiLine(
            showClearIcon = SDGThumbnailsShowClearIcon.True(onClick = {}),
        ),
    )
}
