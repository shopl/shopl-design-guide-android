package com.shopl.sdg.component.attachment_element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.attachment_element.preview.SDGAttachmentElementPreviewParameterProvider
import com.shopl.sdg.component.attachment_element.preview.SDGAttachmentElementPreviewParams
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGAsyncImage
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private val ATTACHMENT_THUMBNAIL_HEIGHT_SIZE = 36.dp
private val ATTACHMENT_ICON_SIZE = 14.dp
private val UPLOADING_INDICATOR_SIZE = 22.dp
private val UPLOADING_INDICATOR_STROKE_WIDTH = 2.dp
private val FAILED_INDICATOR_SIZE = 24.dp
private val UPLOADING_INDICATOR_OFFSET_X = 94.dp
private val UPLOADING_INDICATOR_OFFSET_Y = 7.dp
private val FAILED_INDICATOR_OFFSET_X = 93.dp
private val FAILED_INDICATOR_OFFSET_Y = 6.dp
private const val INACTIVE_CONTENT_ALPHA = 0.1f

/**
 * SDG - Component - Attachment Element [2.3.40]
 *
 * @version 2.3.40
 *
 * 사진, 문서, 동영상 등 첨부된 다양한 미디어 파일의 메타데이터와 포맷별 인디케이터를 시각화하는 컴포넌트
 *
 * @param fileName 확장자를 포함한 첨부 파일명
 * @param fileSize 괄호를 제외한 첨부 파일 크기
 * @param state 첨부 파일 상태
 * @param type 첨부 파일 유형
 * @param marginValues 컴포넌트 외부 여백
 * @param imageModel 사진 또는 동영상 썸네일 모델
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27174-42696&m=dev">Figma</a>
 */
@Composable
fun SDGAttachmentElement(
    fileName: String,
    fileSize: String,
    state: SDGAttachmentElementState,
    type: SDGAttachmentElementType,
    marginValues: PaddingValues = PaddingValues(),
    imageModel: Any? = null,
) {
    val contentAlpha = if (state != SDGAttachmentElementState.Default) {
        INACTIVE_CONTENT_ALPHA
    } else {
        1f
    }

    Row(
        modifier = Modifier
            .padding(marginValues)
            .height(ATTACHMENT_THUMBNAIL_HEIGHT_SIZE),
        horizontalArrangement = spacedBy(SDGSpacing.Spacing12),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AttachmentThumbnail(
            type = type,
            imageModel = imageModel,
            modifier = Modifier.alpha(contentAlpha),
        )

        Box(
            modifier = Modifier
                .weight(weight = 1f, fill = false)
                .height(ATTACHMENT_THUMBNAIL_HEIGHT_SIZE),
        ) {
            Column(
                modifier = Modifier.alpha(contentAlpha),
            ) {
                SDGText(
                    text = fileName,
                    textColor = SDGColor.Neutral600,
                    typography = SDGTypography.Body2R,
                    maxLines = 1,
                    overflow = TextOverflow.MiddleEllipsis,
                )
                SDGText(
                    text = "($fileSize)",
                    textColor = SDGColor.Neutral400,
                    typography = SDGTypography.Body3R,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            when (state) {
                SDGAttachmentElementState.Default -> Unit
                SDGAttachmentElementState.Uploading -> {
                    CircularProgressIndicator(
                        color = SDGColor.Primary300,
                        modifier = Modifier
                            .offset(
                                x = UPLOADING_INDICATOR_OFFSET_X,
                                y = UPLOADING_INDICATOR_OFFSET_Y,
                            )
                            .size(UPLOADING_INDICATOR_SIZE),
                        strokeWidth = UPLOADING_INDICATOR_STROKE_WIDTH,
                    )
                }

                SDGAttachmentElementState.Failed -> {
                    SDGImage(
                        modifier = Modifier
                            .offset(
                                x = FAILED_INDICATOR_OFFSET_X,
                                y = FAILED_INDICATOR_OFFSET_Y,
                            )
                            .size(FAILED_INDICATOR_SIZE),
                        resId = R.drawable.ic_retry,
                        color = SDGColor.Red300,
                    )
                }
            }
        }
    }
}

@Composable
private fun AttachmentThumbnail(
    type: SDGAttachmentElementType,
    imageModel: Any?,
    modifier: Modifier,
) {
    when (type) {
        SDGAttachmentElementType.Document -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(ATTACHMENT_THUMBNAIL_HEIGHT_SIZE)
                    .clip(SDGCornerRadius.BoxRadius.Radius4)
                    .background(SDGColor.Neutral150),
            ) {
                SDGImage(
                    modifier = Modifier.size(ATTACHMENT_ICON_SIZE),
                    resId = R.drawable.ic_clip,
                    color = SDGColor.Secondary200,
                )
            }
        }

        SDGAttachmentElementType.Photo,
        SDGAttachmentElementType.Video -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(ATTACHMENT_THUMBNAIL_HEIGHT_SIZE)
                    .clip(SDGCornerRadius.BoxRadius.Radius4),
            ) {
                AttachmentMediaThumbnail(imageModel = imageModel)

                if (type == SDGAttachmentElementType.Video) {
                    SDGImage(
                        modifier = Modifier.size(ATTACHMENT_ICON_SIZE),
                        resId = R.drawable.ic_common_play,
                        color = null,
                    )
                }
            }
        }
    }
}

@Composable
private fun AttachmentMediaThumbnail(
    imageModel: Any?,
) {
    if (imageModel == null) {
        AttachmentMediaPlaceholder()

        return
    }

    SDGAsyncImage(
        modifier = Modifier.fillMaxSize(),
        imageModel = imageModel,
        failureImage = { AttachmentMediaPlaceholder() },
        contentScale = ContentScale.Crop,
        previewContent = { AttachmentMediaPreview(imageModel = imageModel) },
    )
}

@Composable
private fun AttachmentMediaPreview(
    imageModel: Any?,
) {
    if (imageModel is Int) {
        SDGImage(
            modifier = Modifier.fillMaxSize(),
            resId = imageModel,
            color = null,
            contentScale = ContentScale.Crop,
        )
    } else {
        AttachmentMediaPlaceholder()
    }
}

@Composable
private fun AttachmentMediaPlaceholder() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(SDGColor.Neutral150),
    ) {
        SDGImage(
            modifier = Modifier.size(ATTACHMENT_ICON_SIZE),
            resId = R.drawable.ic_common_photo,
            color = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGAttachmentElement(
    @PreviewParameter(SDGAttachmentElementPreviewParameterProvider::class)
    params: SDGAttachmentElementPreviewParams,
) {
    SDGAttachmentElement(
        fileName = params.fileName,
        fileSize = params.fileSize,
        state = params.state,
        type = params.type,
        imageModel = if (params.type == SDGAttachmentElementType.Document) {
            null
        } else {
            R.drawable.profile_small
        },
    )
}
