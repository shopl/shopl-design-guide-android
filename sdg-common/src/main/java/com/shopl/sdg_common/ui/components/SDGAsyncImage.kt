package com.shopl.sdg_common.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.animation.ShimmerAnimation
import com.shopl.sdg_resource.R

/**
 * 공통 [AsyncImage] - 실패 시 실패 이미지
 *
 * @param modifier                 이미지에 적용할 Modifier
 * @param imageModel               불러올 이미지 모델(URL, URI, 리소스 ID 등)
 * @param isUseShimmer             로딩 중 Shimmer 애니메이션 사용 여부
 * @param failureImageResourceId   실패 시 대체로 표시할 Drawable 리소스 ID (null이면 표시하지 않음)
 * @param failureImageTint         실패 이미지에 적용할 Tint 색상 (null이면 필터 없음)
 * @param contentScale             이미지 크기 조절 방식 (기본값은 ContentScale.Fit)
 * @param filterQuality            이미지 필터링 품질 (기본값은 DefaultFilterQuality)
 * @param colorFilter              이미지에 적용할 ColorFilter (null이면 필터 없음)
 * @param contentDescription       접근성(스크린 리더)용 설명 텍스트
 * @param previewContent           Preview에서 표시할 Composable 람다
 */
@Composable
fun SDGAsyncImage(
    modifier: Modifier,
    imageModel: Any? = null,
    isUseShimmer: Boolean = false,
    failureImageResourceId: Int? = null,
    failureImageTint: Color? = null,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
    colorFilter: ColorFilter? = null,
    contentDescription: String? = null,
    previewContent: (@Composable (() -> Unit)) = {
        SDGImage(
            resId = R.drawable.ic_common_photo,
            color = SDGColor.Neutral0,
            modifier = modifier,
        )
    },
) {
    SDGAsyncImage(
        modifier = modifier,
        imageModel = imageModel,
        isUseShimmer = isUseShimmer,
        failureImage = {
            failureImageResourceId?.let { resourceId ->
                SDGImage(
                    resId = resourceId,
                    color = failureImageTint,
                    contentDescription = contentDescription,
                )
            }
        },
        contentScale = contentScale,
        filterQuality = filterQuality,
        colorFilter = colorFilter,
        contentDescription = contentDescription,
        previewContent = previewContent,
    )
}

/**
 * 공통 [AsyncImage] - 실패 시 Composable
 *
 * @param modifier           이미지에 적용할 Modifier
 * @param imageModel         불러올 이미지 모델(URL, URI, 리소스 ID 등)
 * @param isUseShimmer       로딩 중 Shimmer 애니메이션 사용 여부
 * @param failureImage       실패 시 표시할 Composable 람다
 * @param contentScale       이미지 크기 조절 방식 (기본값은 ContentScale.Fit)
 * @param filterQuality      이미지 필터링 품질 (기본값은 DefaultFilterQuality)
 * @param colorFilter        이미지에 적용할 ColorFilter (null이면 필터 없음)
 * @param contentDescription 접근성(스크린 리더)용 설명 텍스트
 * @param previewContent     Preview에서 표시할 Composable 람다
 */
@Composable
fun SDGAsyncImage(
    modifier: Modifier,
    imageModel: Any? = null,
    isUseShimmer: Boolean = false,
    failureImage: @Composable (() -> Unit),
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = DefaultFilterQuality,
    colorFilter: ColorFilter? = null,
    contentDescription: String? = null,
    previewContent: (@Composable (() -> Unit)) = {
        SDGImage(
            resId = R.drawable.ic_common_photo,
            color = SDGColor.Neutral0,
            modifier = modifier,
        )
    },
) {
    if (LocalInspectionMode.current) {
        previewContent()

        return
    }

    SDGAsyncImageContent(
        modifier = modifier,
        imageModel = imageModel,
        isUseShimmer = isUseShimmer,
        failureImage = failureImage,
        contentScale = contentScale,
        filterQuality = filterQuality,
        colorFilter = colorFilter,
        contentDescription = contentDescription,
    )
}

@Composable
private fun SDGAsyncImageContent(
    modifier: Modifier,
    imageModel: Any?,
    isUseShimmer: Boolean,
    failureImage: @Composable (() -> Unit),
    contentScale: ContentScale,
    filterQuality: FilterQuality,
    colorFilter: ColorFilter?,
    contentDescription: String?,
) {
    var imageState by remember(imageModel) {
        mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty)
    }
    val isSuccess = imageState is AsyncImagePainter.State.Success

    Box(
        modifier = modifier,
        propagateMinConstraints = true,
    ) {
        AsyncImage(
            model = imageModel,
            contentDescription = if (isSuccess) contentDescription else null,
            onState = { imageState = it },
            contentScale = contentScale,
            alpha = if (isSuccess) 1f else 0f,
            colorFilter = colorFilter,
            filterQuality = filterQuality,
        )

        if (isUseShimmer && imageState is AsyncImagePainter.State.Loading) {
            ShimmerAnimation(modifier = Modifier.matchParentSize())
        }

        if (imageState is AsyncImagePainter.State.Error) {
            failureImage()
        }
    }
}

@Preview(
    name = "SDGAsyncImage Failure Image Preview",
    showBackground = true,
    backgroundColor = 0xFFF0F0F0,
)
@Composable
private fun SDGAsyncImagePreview() {
    SDGAsyncImage(
        modifier = Modifier.size(size = 64.dp),
        imageModel = "imageUrl",
    )
}
