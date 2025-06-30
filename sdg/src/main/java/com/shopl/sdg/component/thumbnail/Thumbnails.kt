package com.shopl.sdg.component.thumbnail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGAsyncImage
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

private const val MAX_VISIBLE_IMAGES = 4

/**
 * SDG - Component - Thumbnails
 *
 * 사진 파일의 이미지를 소형화한 컴포넌트
 *
 * @param singleLine 1줄로 표기되는 경우 사용, true인 경우 이미지는 4장까지만 노출되고 더 있는 경우 + N으로 표기됨
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6870-15446&t=MczCJoG28XDGLRPP-4">Figma</a>
 */
@Composable
fun Thumbnails(
    imageModels: PersistentList<Any>,
    singleLine: Boolean,
    onClickImage: (index: Int) -> Unit,
    failureImageBackgroundColor: Color = SDGColor.Neutral0,
    deletable: Boolean = false,
    onClickDelete: ((index: Int) -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if(singleLine) {
            ImageRow(
                imageModels = imageModels,
                onClickImage = onClickImage,
                totalImagesCount = imageModels.size,
                failureImageBackgroundColor = failureImageBackgroundColor,
                deletable = deletable,
                onClickDelete = onClickDelete
            )
        } else {
            imageModels.chunked(4).forEach {
                ImageRow(
                    imageModels = it.toPersistentList(),
                    onClickImage = onClickImage,
                    failureImageBackgroundColor = failureImageBackgroundColor,
                    deletable = deletable,
                    onClickDelete = onClickDelete
                )
            }
        }
    }
}

@Composable
private fun ImageRow(
    imageModels: PersistentList<Any>,
    onClickImage: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    failureImageBackgroundColor: Color,
    totalImagesCount: Int = MAX_VISIBLE_IMAGES,
    deletable: Boolean = false,
    onClickDelete: ((index: Int) -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        imageModels.take(MAX_VISIBLE_IMAGES).forEachIndexed { index, imageModel ->
            if (totalImagesCount > MAX_VISIBLE_IMAGES && index == MAX_VISIBLE_IMAGES - 1) {
                OverflowImage(
                    imageModel = imageModel,
                    extraCount = totalImagesCount - MAX_VISIBLE_IMAGES,
                    onClickImage = { onClickImage(index) },
                )
            } else {
                if(deletable) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(hasRipple = false) {
                                onClickImage(index)
                            },
                    ) {
                        SDGAsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(hasRipple = false) {
                                    onClickImage(index)
                                },
                            imageModel = imageModel,
                            contentScale = ContentScale.Crop,
                            isUseShimmer = true,
                            failureImage = {
                                FailureImage(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(color = failureImageBackgroundColor)
                                )
                            }
                        )
                        SDGImage(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                                .clickable(hasRipple = false) {
                                    onClickDelete?.invoke(index)
                                }
                                .padding(2.dp),
                            resId = R.drawable.ic_remove_m,
                            color = null
                        )
                    }
                } else {
                    SDGAsyncImage(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(hasRipple = false) {
                                onClickImage(index)
                            },
                        imageModel = imageModel,
                        contentScale = ContentScale.Crop,
                        isUseShimmer = true,
                        failureImage = {
                            FailureImage(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color = failureImageBackgroundColor)
                            )
                        }
                    )
                }
            }
        }

        repeat(MAX_VISIBLE_IMAGES - imageModels.size) {
            DummyView()
        }
    }
}

@Composable
private fun RowScope.OverflowImage(
    imageModel: Any,
    extraCount: Int,
    onClickImage: () -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(hasRipple = false) {
                onClickImage()
            }
    ) {
        SDGAsyncImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = imageModel,
            isUseShimmer = true,
            contentScale = ContentScale.Crop,
            failureImage = {
                FailureImage(
                    Modifier.fillMaxSize()
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(SDGColor.Neutral900_a40)
        ) {
            SDGText(
                modifier = Modifier.align(Alignment.Center),
                text = "+$extraCount",
                textColor = SDGColor.Neutral0,
                typography = SDGTypography.Body2SB
            )
        }
    }
}

@Composable
private fun FailureImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        SDGImage(
            modifier = Modifier
                .align(Alignment.Center)
                .size(20.dp),
            resId = R.drawable.ic_common_photo,
            color = SDGColor.Neutral250,
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * Row에 일정 너비를 맞추기 위해 weight 처리를 위한 더미 뷰
 */
@Composable
private fun RowScope.DummyView() {
    Spacer(modifier = Modifier.weight(1f))
}

@Preview
@Composable
private fun PreviewThumbnails() {

    val images by remember {
        mutableStateOf(
            persistentListOf(
                "image_url",
                "image_url",
                "image_url",
                "image_url",
                "image_url",
                "image_url",
                "image_url",
                "image_url",
                "image_url",
                "image_url",
            )
        )
    }

    Column(
        modifier = Modifier
            .background(SDGColor.Neutral50)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SDGText(
            text = "Single Line",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB
        )
        Thumbnails(
            imageModels = images,
            singleLine = true,
            onClickImage = {},
        )
        SDGText(
            text = "Multi Line",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB
        )
        Thumbnails(
            imageModels = images,
            singleLine = false,
            onClickImage = {},
            deletable = true,
            onClickDelete = {}
        )
    }

}