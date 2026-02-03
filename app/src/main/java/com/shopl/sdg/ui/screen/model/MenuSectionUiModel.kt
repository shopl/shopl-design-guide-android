package com.shopl.sdg.ui.screen.model

import kotlinx.collections.immutable.ImmutableList

internal data class MenuSectionUiModel(
    val sectionName: String,
    val menus: ImmutableList<MenuUiModel>,
)
