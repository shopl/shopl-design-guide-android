package com.shopl.sdg.template.multi_time_picker

import androidx.compose.runtime.Immutable
import com.shopl.sdg_common.ext.orDefault

private const val MAX_HOUR = 23
private const val MAX_MINUTE = 59
private const val TIME_TEXT_ZERO_PADDING_CHAR = '0'

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
        "${hour.toString().padStart(2, TIME_TEXT_ZERO_PADDING_CHAR)}:" +
            min.toString().padStart(2, TIME_TEXT_ZERO_PADDING_CHAR)
}

internal fun HourMin.normalizeToClockRange(): HourMin = copy(
    hour = hour.takeIf { it in 0..MAX_HOUR }.orDefault(),
    min = min.takeIf { it in 0..MAX_MINUTE }.orDefault(),
)
