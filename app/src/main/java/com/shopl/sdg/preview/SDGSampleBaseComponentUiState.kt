package com.shopl.sdg.preview

import com.shopl.sdg.model.SDGSampleBaseTabItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class SDGSampleBaseComponentUiState(
    val componentName: String,
    val componentDescription: String,
    val types: PersistentList<SDGSampleBaseTabItem<Any>>?,
    val specs: PersistentList<SDGSampleBaseTabItem<Any>>?,
    val guideLineDescriptions: PersistentList<String> = persistentListOf()
)
