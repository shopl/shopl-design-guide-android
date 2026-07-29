package com.shopl.sdg.component.time_select_input.preview

import com.shopl.sdg.component.time_select_input.SDGTimeSelectInputField
import com.shopl.sdg.component.time_select_input.SDGTimeSelectInputState

internal data class SDGTimeSelectInputPreviewParams(
    val state: SDGTimeSelectInputState,
    val inputField: SDGTimeSelectInputField,
    val startTimePlaceholder: String,
    val endTimePlaceholder: String,
    val startTime: String?,
    val endTime: String?,
)
