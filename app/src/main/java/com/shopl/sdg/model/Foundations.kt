package com.shopl.sdg.model

import com.shopl.sdg.navigation.FoundationDestination
import kotlinx.collections.immutable.persistentListOf

internal val foundations = persistentListOf(
    FoundationDestination.Color,
    FoundationDestination.CornerRadius,
    FoundationDestination.Iconography,
    FoundationDestination.Spacing,
    FoundationDestination.Typograph,
)