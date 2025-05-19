package com.shopl.sdg_common.util

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter

@Composable
fun SDGAsyncImage(
    modifier: Modifier,
    imageModel: Any? = null,
    isUseShimmer: Boolean = false,
    failureImageResourceId: Int? = null,
    failureImageTint: Color? = null,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    colorFilter: ColorFilter? = null,
) {
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

@Composable
fun SDGAsyncImage(
    modifier: Modifier,
    imageModel: Any? = null,
    isUseShimmer: Boolean = false,
    failureImage: @Composable (() -> Unit),
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    colorFilter: ColorFilter? = null,
) {
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
            failureImage.invoke()
        },
        contentScale = contentScale,
        filterQuality = filterQuality,
        colorFilter = colorFilter,
    )
}