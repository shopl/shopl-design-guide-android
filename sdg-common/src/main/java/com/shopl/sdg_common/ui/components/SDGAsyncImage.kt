package com.shopl.sdg_common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.animation.ShimmerAnimation
import com.shopl.sdg_resource.R

/**
 * 공통 [SubcomposeAsyncImage] - 실패 시 실패 이미지
 *
 * @param imageModel               불러올 이미지 모델(URL, URI, 리소스 ID 등)
 * @param isUseShimmer             로딩 중 Shimmer 애니메이션 사용 여부
 * @param failureImageResourceId   실패 시 대체로 표시할 Drawable 리소스 ID (null이면 표시하지 않음)
 * @param failureImageTint         실패 이미지에 적용할 Tint 색상 (null이면 필터 없음)
 * @param contentScale             이미지 크기 조절 방식 (기본값은 ContentScale.Fit)
 * @param filterQuality            이미지 필터링 품질 (기본값은 DefaultFilterQuality)
 * @param colorFilter              이미지에 적용할 ColorFilter (null이면 필터 없음)
 * @param contentDescription       접근성(스크린 리더)용 설명 텍스트
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
        )
    },
) {
    if (LocalInspectionMode.current) {
        previewContent()

        return
    }

    SubcomposeAsyncImage(
        model = imageModel,
        contentDescription = contentDescription,
        modifier = modifier,
        loading = {
            if (isUseShimmer) {
                ShimmerAnimation(
                    modifier = modifier
                )
            }
        },
        error = {
            Image(
                painter = rememberAsyncImagePainter(model = failureImageResourceId),
                contentDescription = contentDescription,
                colorFilter = failureImageTint?.let { ColorFilter.tint(it) }
            )
        },
        contentScale = contentScale,
        filterQuality = filterQuality,
        colorFilter = colorFilter,
    )
}

/**
 * 공통 [SubcomposeAsyncImage] - 실패 시 Composable
 *
 * @param isUseShimmer       로딩 중 Shimmer 애니메이션 사용 여부
 * @param failureImage       실패 시 표시할 Composable 람다
 * @param contentScale       이미지 크기 조절 방식 (기본값은 ContentScale.Fit)
 * @param filterQuality      이미지 필터링 품질 (기본값은 DefaultFilterQuality)
 * @param colorFilter        이미지에 적용할 ColorFilter (null이면 필터 없음)
 * @param contentDescription 접근성(스크린 리더)용 설명 텍스트
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
        )
    },
) {
    if (LocalInspectionMode.current) {
        previewContent()

        return
    }

    SubcomposeAsyncImage(
        model = imageModel,
        contentDescription = contentDescription,
        modifier = modifier,
        loading = {
            if (isUseShimmer) {
                ShimmerAnimation(
                    modifier = modifier
                )
            }
        },
        error = {
            failureImage()
        },
        contentScale = contentScale,
        filterQuality = filterQuality,
        colorFilter = colorFilter,
    )
}

@Preview(
    name = "SDGAsyncImage Failure Image Preview",
    showBackground = true,
    backgroundColor = 0xFFF0F0F0
)
@Composable
private fun SDGAsyncImagePreview() {
    SDGAsyncImage(
        modifier = Modifier.size(64.dp),
        imageModel = "imageUrl",
    )
}
