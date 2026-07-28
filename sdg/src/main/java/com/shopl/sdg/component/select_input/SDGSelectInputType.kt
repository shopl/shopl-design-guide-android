package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Immutable

/**
 * [SDGSelectInput]에 표시되는 Selected Element 유형과 유형별 데이터를 관리합니다.
 */
@Immutable
sealed class SDGSelectInputType {
    abstract val text: String

    abstract val typeName: String

    data class Text(
        override val text: String,
    ) : SDGSelectInputType() {
        override val typeName: String = "Text"
    }

    data class Avatar(
        override val text: String,
        val selectedElementImage: SDGSelectInputImage,
    ) : SDGSelectInputType() {
        override val typeName: String = "Avatar"
    }

    data class OneImage(
        override val text: String,
        val image: SDGSelectInputImage,
        val type: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    ) : SDGSelectInputType() {
        override val typeName: String = "1 Image"
    }

    data class TwoImage(
        override val text: String,
        val image: SDGSelectInputImage,
        val secondText: String,
        val secondImage: SDGSelectInputImage,
        val imageSize: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    ) : SDGSelectInputType() {
        override val typeName: String = "2 Image"
    }
}
