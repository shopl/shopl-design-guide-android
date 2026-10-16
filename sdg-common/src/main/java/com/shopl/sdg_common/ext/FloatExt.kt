package com.shopl.sdg_common.ext


fun Float?.orDefault(defaultValue: Float = 0f): Float = this ?: defaultValue