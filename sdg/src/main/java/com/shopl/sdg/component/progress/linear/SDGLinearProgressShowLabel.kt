package com.shopl.sdg.component.progress.linear

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** [SDGLinearProgress] 라벨의 표시 여부와 표시할 내용을 정의합니다. */
@Immutable
sealed interface SDGLinearProgressShowLabel {
    data class True(
        val text: String,
        val textColor: Color,
    ) : SDGLinearProgressShowLabel

    data object False : SDGLinearProgressShowLabel
}
