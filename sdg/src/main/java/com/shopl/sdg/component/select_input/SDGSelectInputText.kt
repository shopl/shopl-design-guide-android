package com.shopl.sdg.component.select_input

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.style.TextOverflow

/**
 * [SDGSelectInputType]에 표시되는 선택 텍스트와 말줄임 정책을 관리합니다.
 */
@Immutable
sealed class SDGSelectInputText {
    abstract val displayText: String
    abstract val overflow: TextOverflow

    /** 단일 선택 텍스트입니다. */
    data class Single(
        val value: String,
    ) : SDGSelectInputText() {
        override val displayText: String = value
        override val overflow: TextOverflow = TextOverflow.Ellipsis
    }

    /** 다중 선택 텍스트입니다. */
    data class Multiple(
        val value: String,
        val additionalCount: Int,
    ) : SDGSelectInputText() {
        init {
            require(additionalCount > 0) { "추가 선택 개수는 1 이상이어야 합니다." }
        }

        override val displayText: String = "$value+$additionalCount"
        override val overflow: TextOverflow = TextOverflow.MiddleEllipsis
    }

    /** 레거시 API의 텍스트와 말줄임 설정을 보존합니다. */
    internal data class Legacy(
        override val displayText: String,
        override val overflow: TextOverflow,
    ) : SDGSelectInputText()
}
