package com.shopl.sdg.template.common_select_view.preview

import com.shopl.sdg.template.common_select_view.SDGCommonSelectViewState
import kotlinx.collections.immutable.PersistentList

internal data class SDGCommonSelectViewPreviewParams(
    val title: String,
    val searchInput: String,
    val state: SDGCommonSelectViewState,
    val items: PersistentList<String>,
)
