package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Immutable

/**
 * [SDGSelectInput]에 표시되는 Selected Element 유형과 유형별 데이터를 관리합니다.
 */
@Immutable
sealed class SDGSelectInputType {
    /** 첫 번째 선택 항목의 텍스트입니다. */
    abstract val text: SDGSelectInputText

    abstract val typeName: String

    data class Text(
        override val text: SDGSelectInputText,
    ) : SDGSelectInputType() {
        override val typeName: String = "Text"
    }

    data class Avatar(
        override val text: SDGSelectInputText,
        val selectedElementImage: SDGSelectInputImage,
    ) : SDGSelectInputType() {
        override val typeName: String = "Avatar"
    }

    data class OneImage(
        override val text: SDGSelectInputText,
        val image: SDGSelectInputImage,
        val type: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    ) : SDGSelectInputType() {
        override val typeName: String = "1 Image"
    }

    data class TwoImage(
        val first: SDGSelectInputImageElement,
        val second: SDGSelectInputImageElement,
    ) : SDGSelectInputType() {
        override val text: SDGSelectInputText
            get() = first.text

        override val typeName: String = "2 Image"
    }
}

/** Two Image를 구성하는 독립적인 One Image Input 항목입니다. */
@Immutable
data class SDGSelectInputImageElement(
    val text: SDGSelectInputText,
    val image: SDGSelectInputImage,
    val type: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    val state: SDGSelectInputState? = null,
)
