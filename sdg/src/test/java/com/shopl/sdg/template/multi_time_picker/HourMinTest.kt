package com.shopl.sdg.template.multi_time_picker

import org.junit.Assert.assertEquals
import org.junit.Test

class HourMinTest {
    @Test
    fun normalizeToClockRange_replacesValuesOutsideThePickerRangeWithZero() {
        assertEquals(HourMin(), HourMin(hour = 24, min = 60).normalizeToClockRange())
    }
}
