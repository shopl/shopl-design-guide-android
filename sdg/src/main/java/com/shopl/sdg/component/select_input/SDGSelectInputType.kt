package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Immutable

/**
 * [SDGSelectInput]에 표시되는 Selected Element 유형과 유형별 데이터를 관리합니다.
 */
@Immutable
sealed class SDGSelectInputType {
    abstract val typeName: String

    data object Text : SDGSelectInputType() {
        override val typeName: String = "Text"
    }

    data class Avatar(
        val selectedElementImage: SDGSelectInputImage,
    ) : SDGSelectInputType() {
        override val typeName: String = "Avatar"
    }

    data class OneImage(
        val image: SDGSelectInputImage,
        val type: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    ) : SDGSelectInputType() {
        override val typeName: String = "1 Image"
    }

    data class TwoImage(
        val image: SDGSelectInputImage,
        val secondText: String,
        val secondSelectedElementImage: SDGSelectInputImage,
        val imageSize: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    ) : SDGSelectInputType() {
        override val typeName: String = "2 Image"
    }
}
