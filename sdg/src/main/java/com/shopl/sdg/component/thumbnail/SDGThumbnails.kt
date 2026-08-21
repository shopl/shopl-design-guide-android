package com.shopl.sdg.component.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailUiModel
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsLine
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsShowClearIcon
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsType
import com.shopl.sdg.component.thumbnail.preview.SDGThumbnailsPreviewParameterProvider
import com.shopl.sdg.component.thumbnail.preview.SDGThumbnailsPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGAsyncImage
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

internal const val THUMBNAILS_PER_ROW = 4
private const val THUMBNAIL_ASPECT_RATIO = 1f
private const val THUMBNAIL_OVERFLOW_MAX_COUNT = 999
private const val THUMBNAIL_PLAY_BUTTON_BACKGROUND_ALPHA = 0.5f
private const val THUMBNAIL_PLAY_BUTTON_ICON_ROTATION_DEGREES = 90f

/**
 * SDG - Component - Thumbnails
 *
 * 여러 장의 사진을 4분할 격자(Grid) 형태로 정렬하는 통합 썸네일 레이아웃 컴포넌트
 *
 * @version 2.3.43
 *
 * @param type 썸네일 유형과 썸네일 목록 및 클릭 이벤트
 * @param line 썸네일 표시 방식과 클리어 아이콘 설정
 * @param failureImageBackgroundColor 이미지 로드 실패 시 표시할 배경색
 * @param marginValues 컴포넌트 외부 여백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6870-15446&m=dev">Figma</a>
 */
@Composable
fun SDGThumbnails(
    type: SDGThumbnailsType,
    line: SDGThumbnailsLine,
    failureImageBackgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
) {
    val visibleThumbnails = when (line) {
        is SDGThumbnailsLine.SingleLine -> type.thumbnails.take(n = THUMBNAILS_PER_ROW)
        is SDGThumbnailsLine.MultiLine -> type.thumbnails
    }

    Column(
        modifier = Modifier
            .padding(paddingValues = marginValues)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing8),
    ) {
        visibleThumbnails
            .chunked(size = THUMBNAILS_PER_ROW)
            .forEachIndexed { rowIndex, thumbnails ->
                SDGThumbnailRow(
                    modifier = Modifier.fillMaxWidth(),
                    thumbnails = thumbnails,
                    type = type,
                    line = line,
                    rowStartIndex = rowIndex * THUMBNAILS_PER_ROW,
                    failureImageBackgroundColor = failureImageBackgroundColor,
                )
            }
    }
}

@Composable
internal fun SDGThumbnailRow(
    thumbnails: List<SDGThumbnailUiModel>,
    type: SDGThumbnailsType,
    line: SDGThumbnailsLine,
    rowStartIndex: Int,
    failureImageBackgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    val showClearIcon = when (line) {
        is SDGThumbnailsLine.SingleLine -> SDGThumbnailsShowClearIcon.False
        is SDGThumbnailsLine.MultiLine -> line.showClearIcon
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing8),
    ) {
        thumbnails.forEachIndexed { index, thumbnail ->
            val thumbnailIndex = rowStartIndex + index
            val shouldShowOverflowOverlay =
                line is SDGThumbnailsLine.SingleLine &&
                        type.thumbnails.size > THUMBNAILS_PER_ROW &&
                        index == thumbnails.lastIndex

            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .aspectRatio(ratio = THUMBNAIL_ASPECT_RATIO)
                    .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
                    .clickable(
                        onClick = { type.onClick(thumbnailIndex) },
                    ),
            ) {
                SDGAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = thumbnail.thumbnail,
                    isUseShimmer = true,
                    failureImage = {
                        SDGThumbnailFailureImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = failureImageBackgroundColor),
                        )
                    },
                    contentScale = ContentScale.Crop,
                )

                if (type is SDGThumbnailsType.Video) {
                    SDGThumbnailPlayButton()
                }

                if (shouldShowOverflowOverlay) {
                    val overflowThumbnailCount = (type.thumbnails.size - thumbnailIndex)
                        .coerceAtMost(maximumValue = THUMBNAIL_OVERFLOW_MAX_COUNT)

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = SDGColor.Neutral900_a40),
                        contentAlignment = Alignment.Center,
                    ) {
                        SDGText(
                            text = "+$overflowThumbnailCount",
                            textColor = SDGColor.Neutral0,
                            typography = SDGTypography.Body2SB,
                        )
                    }
                }

                if (showClearIcon is SDGThumbnailsShowClearIcon.True) {
                    SDGThumbnailClearIcon(
                        onClick = { showClearIcon.onClick(thumbnailIndex) },
                    )
                }
            }
        }

        repeat(times = THUMBNAILS_PER_ROW - thumbnails.size) {
            Spacer(modifier = Modifier.weight(weight = 1f))
        }
    }
}

@Composable
private fun SDGThumbnailFailureImage(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        SDGImage(
            modifier = Modifier.size(size = SDGSpacing.Spacing20),
            resId = R.drawable.ic_common_photo,
            color = SDGColor.Neutral250,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun BoxScope.SDGThumbnailPlayButton() {
    Box(
        modifier = Modifier
            .align(alignment = Alignment.Center)
            .size(size = SDGSpacing.Spacing20)
            .background(
                color = SDGColor.Neutral700.copy(alpha = THUMBNAIL_PLAY_BUTTON_BACKGROUND_ALPHA),
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        SDGImage(
            modifier = Modifier
                .size(size = 14.dp)
                .offset(x = SDGSpacing.Spacing1)
                .rotate(degrees = THUMBNAIL_PLAY_BUTTON_ICON_ROTATION_DEGREES),
            resId = R.drawable.ic_common_triangleup,
            color = SDGColor.Neutral0,
        )
    }
}

@Composable
private fun BoxScope.SDGThumbnailClearIcon(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .align(alignment = Alignment.TopEnd)
            .size(size = SDGSpacing.Spacing24)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        SDGImage(
            modifier = Modifier.size(size = SDGSpacing.Spacing20),
            resId = R.drawable.ic_remove_m,
            color = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGThumbnails(
    @PreviewParameter(provider = SDGThumbnailsPreviewParameterProvider::class)
    params: SDGThumbnailsPreviewParams,
) {
    SDGThumbnails(
        type = params.type,
        line = params.line,
        failureImageBackgroundColor = SDGColor.Neutral0,
        marginValues = PaddingValues(),
    )
}
