package com.shopl.sdg.component.dropdown.preview

import com.shopl.sdg.component.dropdown.SDGDropdownInputField
import com.shopl.sdg.component.dropdown.SDGDropdownState

internal data class SDGDropdownPreviewParams(
    val text: String,
    val placeholder: String,
    val state: SDGDropdownState,
    val inputField: SDGDropdownInputField,
)
