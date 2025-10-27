package com.shopl.sdg_common.foundation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * SDG - Foundation - Corner Radius
 * 화면 및 카드의 Radius 값 정의
 */
object SDGCornerRadius {

    val Radius4 = 4.dp
    val Radius8 = 8.dp
    val Radius12 = 12.dp
    val Radius16 = 16.dp
    val Radius20 = 20.dp

    /**
     * SDG - Foundation - Corner Radius - Box Radius
     * 20이 기본이며, 콘텐츠의 양에 따라 16/12, 8/4 사용
     */
    object BoxRadius {
        /**
         * Common 기본 단위
         */
        val Radius4 = RoundedCornerShape(4.dp)
        val Radius8 = RoundedCornerShape(8.dp)
        val Radius12 = RoundedCornerShape(12.dp)
        val Radius16 = RoundedCornerShape(16.dp)
        val Radius20 = RoundedCornerShape(20.dp)

        /**
         * Special 예외 단위
         */
        val Radius2 = RoundedCornerShape(2.dp)
        val Radius6 = RoundedCornerShape(6.dp)
        val Radius10 = RoundedCornerShape(10.dp)

        /**
         * Etc 일부 컴포넌트에 사용된 단위
         */
        internal val Radius14 = RoundedCornerShape(14.dp)
        internal val Radius18 = RoundedCornerShape(18.dp)
        internal val Radius25 = RoundedCornerShape(25.dp)
    }

}