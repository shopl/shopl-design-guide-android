package com.shopl.sdg_common.ui.animation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

/**
 * 전달된 브러시를 배경으로 하는 Spacer를 렌더링해 Shimmer 영역 표시
 *
 * @param brush     Shimmer 애니메이션 브러시
 */
@Composable
fun ShimmerItem(
    brush: Brush,
    modifier: Modifier
) {
    Spacer(
        modifier = modifier
            .background(brush = brush)
    )
}