package com.shopl.sdg.component.list_header_label.preview

import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelShowCount
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelShowDropdown

internal data class SDGListHeaderLabelPreviewParams(
    val label: String,
    val count: SDGListHeaderLabelShowCount,
    val showDropdown: SDGListHeaderLabelShowDropdown,
)
