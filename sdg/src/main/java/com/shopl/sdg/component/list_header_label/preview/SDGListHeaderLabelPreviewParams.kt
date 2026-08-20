package com.shopl.sdg.component.list_header_label.preview

import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelCount
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelShowDropdown

internal data class SDGListHeaderLabelPreviewParams(
    val label: String,
    val count: SDGListHeaderLabelCount,
    val showDropdown: SDGListHeaderLabelShowDropdown,
)
