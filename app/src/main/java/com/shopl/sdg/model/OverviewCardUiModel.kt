package com.shopl.sdg.model

import com.shopl.sdg.scene.SDGScene
import kotlinx.collections.immutable.PersistentList

internal data class OverviewCardUiModel(
    val title: String,
    val description: String,
    val scenes: PersistentList<SDGScene>,
)
