package com.shopl.sdg.component.thumbnail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailUiModel
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsLine
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsShowClearIcon
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsType

internal class SDGThumbnailsPreviewParameterProvider :
    PreviewParameterProvider<SDGThumbnailsPreviewParams> {

    override val values: Sequence<SDGThumbnailsPreviewParams> = sequenceOf(
        사진_싱글_오버레이_10개_표기(),
        사진_멀티_클리어아이콘_미노출(),
        사진_멀티_클리어아이콘_노출(),
        영상_싱글_3개(),
        영상_싱글_오버레이_999개_표기(),
        영상_멀티_클리어아이콘_미노출(),
        영상_멀티_클리어아이콘_노출(),
    )

    private fun 사진_싱글_오버레이_10개_표기() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Photo(
            onClick = {},
            thumbnails = List(
                size = 13,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "photo_single_overlay_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.SingleLine,
    )

    private fun 사진_멀티_클리어아이콘_미노출() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Photo(
            onClick = {},
            thumbnails = List(
                size = 12,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "photo_multi_clear_false_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.MultiLine(
            showClearIcon = SDGThumbnailsShowClearIcon.False,
        ),
    )

    private fun 사진_멀티_클리어아이콘_노출() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Photo(
            onClick = {},
            thumbnails = List(
                size = 12,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "photo_multi_clear_true_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.MultiLine(
            showClearIcon = SDGThumbnailsShowClearIcon.True(onClick = {}),
        ),
    )

    private fun 영상_싱글_3개() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Video(
            onClick = {},
            thumbnails = List(
                size = 3,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "video_single_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.SingleLine,
    )

    private fun 영상_싱글_오버레이_999개_표기() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Video(
            onClick = {},
            thumbnails = List(
                size = 1_002,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "video_single_overlay_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.SingleLine,
    )

    private fun 영상_멀티_클리어아이콘_미노출() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Video(
            onClick = {},
            thumbnails = List(
                size = 12,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "video_multi_clear_false_$index",
                        thumbnail = "image_url_$index",
                    )
                },
            ),
        ),
        mode = SDGThumbnailsLine.MultiLine(
            showClearIcon = SDGThumbnailsShowClearIcon.False,
        ),
    )

    private fun 영상_멀티_클리어아이콘_노출() = SDGThumbnailsPreviewParams(
        type = SDGThumbnailsType.Video(
            onClick = {},
            thumbnails = List(
                size = 12,
                init = { index ->
                    SDGThumbnailUiModel(
                        id = "video_multi_clear_true_$index",
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
