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

        @Deprecated(
            message = "String 대신 SDGSelectInputText를 사용하세요.",
        )
        constructor(text: String) : this(
            text = SDGSelectInputText.Single(value = text),
        )
    }

    data class Avatar(
        override val text: SDGSelectInputText,
        val selectedElementImage: SDGSelectInputImage,
    ) : SDGSelectInputType() {
        override val typeName: String = "Avatar"

        @Deprecated(
            message = "String 대신 SDGSelectInputText를 사용하세요.",
        )
        constructor(
            text: String,
            selectedElementImage: SDGSelectInputImage,
        ) : this(
            text = SDGSelectInputText.Single(value = text),
            selectedElementImage = selectedElementImage,
        )
    }

    data class OneImage(
        override val text: SDGSelectInputText,
        val image: SDGSelectInputImage,
        val type: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    ) : SDGSelectInputType() {
        override val typeName: String = "1 Image"

        @Deprecated(
            message = "String 대신 SDGSelectInputText를 사용하세요.",
        )
        constructor(
            text: String,
            image: SDGSelectInputImage,
            type: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
        ) : this(
            text = SDGSelectInputText.Single(value = text),
            image = image,
            type = type,
        )
    }

    data class TwoImage(
        override val text: SDGSelectInputText,
        val image: SDGSelectInputImage,
        val secondText: SDGSelectInputText,
        val secondImage: SDGSelectInputImage,
        val imageSize: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
    ) : SDGSelectInputType() {
        override val typeName: String = "2 Image"

        @Deprecated(
            message = "String 대신 SDGSelectInputText를 사용하세요.",
        )
        constructor(
            text: String,
            image: SDGSelectInputImage,
            secondText: String,
            secondImage: SDGSelectInputImage,
            imageSize: SDGSelectInputImageType = SDGSelectInputImageType.Normal1,
        ) : this(
            text = SDGSelectInputText.Single(value = text),
            image = image,
            secondText = SDGSelectInputText.Single(value = secondText),
            secondImage = secondImage,
            imageSize = imageSize,
        )
    }
}
