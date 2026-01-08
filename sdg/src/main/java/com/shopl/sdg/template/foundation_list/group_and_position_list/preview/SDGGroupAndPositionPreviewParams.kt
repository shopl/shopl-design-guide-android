package com.shopl.sdg.template.foundation_list.group_and_position_list.preview

import com.shopl.sdg.template.foundation_list.group_and_position_list.SDGGroupUiModel
import com.shopl.sdg.template.foundation_list.group_and_position_list.SDGPositionUiModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class SDGGroupAndPositionPreviewParams(
    val groups: PersistentList<SDGGroupUiModel> = persistentListOf(),
    val positions: PersistentList<SDGPositionUiModel> = persistentListOf(),
)
