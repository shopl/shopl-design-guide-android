package com.shopl.sdg.model

import androidx.compose.runtime.Stable

@Stable
internal data class SDGSampleBaseTabItem<T>(
    val title: String,
    val item: T,
)