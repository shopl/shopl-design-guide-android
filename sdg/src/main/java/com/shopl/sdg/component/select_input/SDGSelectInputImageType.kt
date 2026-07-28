package com.shopl.sdg.component.select_input

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * [SDGSelectInputType.OneImage]와 [SDGSelectInputType.TwoImage]의 이미지 영역 규격입니다.
 */
enum class SDGSelectInputImageType(
    val width: Dp,
    val height: Dp,
) {
    Normal1(
        width = 30.dp,
        height = 30.dp,
    ),
    Normal2(
        width = 24.dp,
        height = 24.dp,
    ),
    Special1(
        width = 52.dp,
        height = 10.dp,
    ),
}
