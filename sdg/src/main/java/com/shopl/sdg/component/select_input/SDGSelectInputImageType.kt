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
    WithCircular(
        width = 30.dp,
        height = 30.dp,
    ),
    Square(
        width = 24.dp,
        height = 24.dp,
    ),
    Rectangle(
        width = 52.dp,
        height = 10.dp,
    ),
    ;

    companion object {
        @Deprecated(
            message = "Normal1 대신 WithCircular를 사용하세요.",
            replaceWith = ReplaceWith("WithCircular"),
        )
        @Suppress("PropertyName")
        val Normal1: SDGSelectInputImageType = WithCircular

        @Deprecated(
            message = "Normal2 대신 Square를 사용하세요.",
            replaceWith = ReplaceWith("Square"),
        )
        @Suppress("PropertyName")
        val Normal2: SDGSelectInputImageType = Square

        @Deprecated(
            message = "Special1 대신 Rectangle을 사용하세요.",
            replaceWith = ReplaceWith("Rectangle"),
        )
        @Suppress("PropertyName")
        val Special1: SDGSelectInputImageType = Rectangle
    }
}
