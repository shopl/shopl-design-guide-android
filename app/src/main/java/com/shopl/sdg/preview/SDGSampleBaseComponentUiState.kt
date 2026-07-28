package com.shopl.sdg.preview

import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg_common.util.emptyPersistentList
import kotlinx.collections.immutable.PersistentList

internal data class SDGSampleBaseComponentUiState(
    val componentName: String,
    val componentDescription: String,
    val types: PersistentList<SDGSampleBaseTabItem<Any>>?,
    val specs: PersistentList<SDGSampleBaseTabItem<Any>>?,
    val guideLineDescriptions: PersistentList<String> = emptyPersistentList()
)
