package com.shopl.sdg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import com.shopl.sdg.screen.OverviewScreen
import com.shopl.sdg.screen.component.AvatarScreen
import com.shopl.sdg.screen.component.button.BoxButtonScreen

internal fun provideNavEntry(destination: SDGDestination): NavEntry<Any> {
    return NavEntry(destination) {
        when (destination) {
            is SDGDestination.Overview -> OverviewScreenRoute()
            is ComponentDestination -> ComponentScreenRoute(destination)
            else -> {}
        }
    }
}

@Composable
private fun OverviewScreenRoute() {
    OverviewScreen()
}

@Composable
private fun ComponentScreenRoute(destination: ComponentDestination) {
    when (destination) {
        is ComponentDestination.Avatar -> {
            AvatarScreen()
        }
        is ComponentDestination.Button -> {
            when(destination) {
                is ComponentDestination.Button.BoxButton -> {
                    BoxButtonScreen()
                }
                else -> {}
            }
        }
        else -> {}
    }
}