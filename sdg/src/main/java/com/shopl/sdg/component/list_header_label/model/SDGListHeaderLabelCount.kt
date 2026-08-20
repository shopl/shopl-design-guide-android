package com.shopl.sdg.component.list_header_label.model

sealed interface SDGListHeaderLabelCount {
    data class True(val countValue: String) : SDGListHeaderLabelCount
    data object False : SDGListHeaderLabelCount
}
