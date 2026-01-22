package com.shopl.sdg.ui.screen.foundation.model

import com.shopl.sdg.scene.SDGScene
import kotlinx.collections.immutable.PersistentList

internal data class OverviewCardUiModel(
    val title: String,
    val description: String,
    val scenes: PersistentList<SDGScene>,
)
