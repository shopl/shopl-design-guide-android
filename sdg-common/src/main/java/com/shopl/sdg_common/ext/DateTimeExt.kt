package com.shopl.sdg_common.ext

import org.joda.time.DateTime
import org.joda.time.Duration
import kotlin.math.abs

fun DateTime.distanceDays(to: DateTime): Int {
    return abs(
        Duration(
            this.withTime(0, 0, 0, 0),
            to.withTime(0, 0, 0, 0)
        ).standardDays.toInt()
    ) + 1
}