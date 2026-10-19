package com.shopl.sdg.component.progress.linear

import androidx.compose.runtime.Immutable

/** Progress 동작 방식입니다. */
@Immutable
sealed interface SDGLinearProgressType {

    /**
     * 진행률에 따라 인디케이터가 채워집니다.
     *
     * @param progress 0f부터 1f까지의 진행률
     */
    data class Determinate(val progress: Float) : SDGLinearProgressType

    /** 일정 길이의 인디케이터가 좌우로 반복 이동합니다. */
    data object Indeterminate : SDGLinearProgressType
}
