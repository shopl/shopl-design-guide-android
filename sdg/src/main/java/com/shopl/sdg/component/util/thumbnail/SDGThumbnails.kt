package com.shopl.sdg.component.util.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.thumbnail.ImageRow
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

/**
 * SDG - Component - Thumbnails for [LazyListScope]
 *
 * 기존 [com.shopl.sdg.component.thumbnail.SDGThumbnails]와 동일한 UI 및 동작을 제공하면서,
 * 각 썸네일 행을 상위 Lazy layout의 item으로 구성합니다.
 *
 * @param singleLine 1줄로 표기되는 경우 사용, true인 경우 이미지는 4장까지만 노출되고 더 있는 경우 + N으로 표기됨
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6870-15446&t=MczCJoG28XDGLRPP-4">Figma</a>
 */

private const val THUMBNAIL_COLUMN_COUNT = 4

private object SDGThumbnailRowContentType

fun LazyListScope.SDGThumbnails(
    imageModels: PersistentList<Any>,
    singleLine: Boolean,
    onClickImage: (index: Int) -> Unit,
    failureImageBackgroundColor: Color = SDGColor.Neutral0,
    deletable: Boolean = false,
    onClickDelete: ((index: Int) -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    val rowCount = if (singleLine) {
        1
    } else {
        (imageModels.size + THUMBNAIL_COLUMN_COUNT - 1) / THUMBNAIL_COLUMN_COUNT
    }

    if (rowCount == 0) {
        item(contentType = SDGThumbnailRowContentType) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = marginValues),
            )
        }
        return
    }

    items(
        count = rowCount,
        contentType = { SDGThumbnailRowContentType },
    ) { rowIndex ->
        val firstImageIndex = rowIndex * THUMBNAIL_COLUMN_COUNT
        val rowImageModels = if (singleLine) {
            imageModels
        } else {
            imageModels
                .subList(
                    fromIndex = firstImageIndex,
                    toIndex = minOf(a = firstImageIndex + THUMBNAIL_COLUMN_COUNT, b = imageModels.size),
                )
                .toPersistentList()
        }
        val layoutDirection = LocalLayoutDirection.current

        ImageRow(
            imageModels = rowImageModels,
            onClickImage = { index ->
                onClickImage(firstImageIndex + index)
            },
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(
                    left = marginValues.calculateLeftPadding(layoutDirection),
                    top = if (rowIndex == 0) marginValues.calculateTopPadding() else SDGSpacing.Spacing8,
                    right = marginValues.calculateRightPadding(layoutDirection),
                    bottom = if (rowIndex == rowCount - 1) {
                        marginValues.calculateBottomPadding()
                    } else {
                        0.dp
                    },
                ),
            failureImageBackgroundColor = failureImageBackgroundColor,
            totalImagesCount = if (singleLine) imageModels.size else THUMBNAIL_COLUMN_COUNT,
            deletable = deletable,
            onClickDelete = onClickDelete?.let { onDelete ->
                { index -> onDelete(firstImageIndex + index) }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLazyScopeSDGThumbnails() {
    val images = remember {
        List(size = 40) { index -> "image_url_$index" }.toPersistentList()
    }

    LazyColumn(
        modifier = Modifier.background(color = SDGColor.Neutral50),
        contentPadding = PaddingValues(all = SDGSpacing.Spacing20),
    ) {
        SDGThumbnails(
            imageModels = images,
            singleLine = false,
            onClickImage = {},
        )
    }
}
