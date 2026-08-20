package com.shopl.sdg.component.list_header_label.model

sealed interface SDGListHeaderLabelShowCount {
    data class True(val countValue: String) : SDGListHeaderLabelShowCount
    data object False : SDGListHeaderLabelShowCount
}
