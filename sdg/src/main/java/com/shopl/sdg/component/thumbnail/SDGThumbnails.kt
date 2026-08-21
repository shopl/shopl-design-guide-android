package com.shopl.sdg.component.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.thumbnail.model.SDGThumbnailsMode
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

private const val THUMBNAILS_PER_ROW = 4
private const val THUMBNAIL_ASPECT_RATIO = 1f
private const val THUMBNAIL_OVERFLOW_MAX_COUNT = 999
private const val THUMBNAIL_PLAY_BUTTON_BACKGROUND_ALPHA = 0.5f
private const val THUMBNAIL_PLAY_BUTTON_ICON_ROTATION_DEGREES = 90f

/**
 * SDG - Component - Thumbnails
 *
 * 사진 또는 영상 썸네일을 4열 그리드로 표시하는 컴포넌트
 *
 * @version 2.3.43
 *
 * @param type 썸네일 유형과 썸네일 목록 및 클릭 이벤트
 * @param mode 썸네일 표시 방식과 클리어 아이콘 설정
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6870-15446&m=dev">Figma</a>
 */
@Composable
fun SDGThumbnails(
    type: SDGThumbnailsType,
    mode: SDGThumbnailsMode,
) {
    val (showClearIcon, visibleThumbnails) = when (mode) {
        is SDGThumbnailsMode.Single -> SDGThumbnailsShowClearIcon.False to type.thumbnails.take(n = THUMBNAILS_PER_ROW)
        is SDGThumbnailsMode.Multi -> mode.showClearIcon to type.thumbnails
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = THUMBNAILS_PER_ROW),
        horizontalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing8),
        verticalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing8),
        content = {
            itemsIndexed(
                items = visibleThumbnails,
                key = { _, thumbnail -> thumbnail.id },
                itemContent = { index, thumbnail ->
                    val shouldShowOverflowOverlay =
                        type.thumbnails.size > visibleThumbnails.size &&
                                index == visibleThumbnails.lastIndex
                    val overflowThumbnailCount = (type.thumbnails.size - index)
                        .coerceAtMost(maximumValue = THUMBNAIL_OVERFLOW_MAX_COUNT)

                    Box(
                        modifier = Modifier
                            .aspectRatio(ratio = THUMBNAIL_ASPECT_RATIO)
                            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
                            .clickable(
                                onClick = { type.onClick(index) },
                            ),
                    ) {
                        SDGAsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            imageModel = thumbnail.thumbnail,
                            contentScale = ContentScale.Crop,
                            isUseShimmer = true,
                        )

                        if (type is SDGThumbnailsType.Video) {
                            SDGThumbnailPlayButton()
                        }

                        if (shouldShowOverflowOverlay) {
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
                                onClick = { showClearIcon.onClick(index) },
                            )
                        }
                    }
                },
            )
        },
    )
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
        content = {
            SDGImage(
                modifier = Modifier.size(size = SDGSpacing.Spacing20),
                resId = R.drawable.ic_remove_m,
                color = null,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGThumbnails(
    @PreviewParameter(provider = SDGThumbnailsPreviewParameterProvider::class)
    params: SDGThumbnailsPreviewParams,
) {
    SDGThumbnails(
        type = params.type,
        mode = params.mode,
    )
}
