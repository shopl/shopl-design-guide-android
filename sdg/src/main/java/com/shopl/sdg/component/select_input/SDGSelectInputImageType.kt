package com.shopl.sdg.component.select_input

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * [SDGSelectInputType.OneImage]와 [SDGSelectInputType.TwoImage]의 이미지 영역 규격입니다.
 *
 * 기존 import와 when 분기 호환성을 위해 enum 항목을 유지하고 새 명칭은 별칭으로 제공합니다.
 */
enum class SDGSelectInputImageType(
    val width: Dp,
    val height: Dp,
) {
    @Deprecated(
        message = "Normal1 대신 WithCircular를 사용하세요.",
        replaceWith = ReplaceWith("com.shopl.sdg.component.select_input.SDGSelectInputImageType.WithCircular"),
    )
    Normal1(
        width = 30.dp,
        height = 30.dp,
    ),
    @Deprecated(
        message = "Normal2 대신 Square를 사용하세요.",
        replaceWith = ReplaceWith("com.shopl.sdg.component.select_input.SDGSelectInputImageType.Square"),
    )
    Normal2(
        width = 24.dp,
        height = 24.dp,
    ),
    @Deprecated(
        message = "Special1 대신 Rectangle을 사용하세요.",
        replaceWith = ReplaceWith("com.shopl.sdg.component.select_input.SDGSelectInputImageType.Rectangle"),
    )
    Special1(
        width = 52.dp,
        height = 10.dp,
    ),
    ;

    @Suppress("DEPRECATION")
    companion object {
        val WithCircular: SDGSelectInputImageType = Normal1
        val Square: SDGSelectInputImageType = Normal2
        val Rectangle: SDGSelectInputImageType = Special1
    }
}
