package com.shopl.sdg.template.multi_time_picker

import androidx.compose.runtime.Immutable

private const val MAX_HOUR = 23
private const val MAX_MINUTE = 59

/**
 * SDG Multi Time Picker에서 사용하는 시각 값입니다.
 */
@Immutable
data class HourMin(
    val hour: Int = 0,
    val min: Int = 0,
) {
    /**
     * 시각을 두 자리 시·분으로 구성된 `HH:mm` 형식의 문자열로 반환합니다.
     */
    fun toTimeText(): String =
        "${hour.toString().padStart(2, '0')}:${min.toString().padStart(2, '0')}"
}

internal fun HourMin.normalizeToClockRange(): HourMin = copy(
    hour = hour.takeIf { it in 0..MAX_HOUR } ?: 0,
    min = min.takeIf { it in 0..MAX_MINUTE } ?: 0,
)
