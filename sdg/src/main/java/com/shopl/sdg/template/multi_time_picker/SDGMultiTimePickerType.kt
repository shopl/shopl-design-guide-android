package com.shopl.sdg.template.multi_time_picker

import androidx.compose.runtime.Stable

/**
 * [SDGMultiTimePicker]가 선택할 시각의 개수를 정의합니다.
 */
@Stable
sealed class SDGMultiTimePickerType {
    data class Single(
        val time: HourMin,
    ) : SDGMultiTimePickerType()

    data class Multi(
        val startTime: HourMin,
        val endTime: HourMin,
    ) : SDGMultiTimePickerType()
}

internal fun SDGMultiTimePickerType.normalizeToClockRange(): SDGMultiTimePickerType = when (this) {
    is SDGMultiTimePickerType.Single -> copy(time = time.normalizeToClockRange())
    is SDGMultiTimePickerType.Multi -> copy(
        startTime = startTime.normalizeToClockRange(),
        endTime = endTime.normalizeToClockRange(),
    )
}

internal fun SDGMultiTimePickerType.selectedTime(target: TimeTarget): HourMin = when (this) {
    is SDGMultiTimePickerType.Single -> time
    is SDGMultiTimePickerType.Multi -> when (target) {
        TimeTarget.Start -> startTime
        TimeTarget.End -> endTime
    }
}

internal fun SDGMultiTimePickerType.updateSelectedTime(
    target: TimeTarget,
    update: (HourMin) -> HourMin,
): SDGMultiTimePickerType = when (this) {
    is SDGMultiTimePickerType.Single -> copy(time = update(time))
    is SDGMultiTimePickerType.Multi -> when (target) {
        TimeTarget.Start -> copy(startTime = update(startTime))
        TimeTarget.End -> copy(endTime = update(endTime))
    }
}
