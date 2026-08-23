package com.shopl.sdg_common.ext

fun Int?.orDefault(defaultValue: Int = 0): Int = this ?: defaultValue
