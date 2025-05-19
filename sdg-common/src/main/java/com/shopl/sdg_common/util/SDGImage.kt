package com.shopl.sdg_common.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * 공통 [Image]
 *
 * @param alignment          이미지 배치 정렬 방식 (기본값은 Alignment.Center)
 * @param contentScale       이미지 크기 조절 방식 (기본값은 ContentScale.Fit)
 * @param alpha              이미지 투명도 (0f부터 1f 사이, 기본값은 DefaultAlpha)
 * @param contentDescription 접근성(스크린 리더)용 설명 텍스트
 */
@Composable
fun SDGImage(
    @DrawableRes resId: Int,
    color: Color?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    contentDescription: String? = null
) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = color?.let { ColorFilter.tint(it) }
    )
}