package com.shopl.sdg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.scene.SDGScene
import com.shopl.sdg.ui.screen.OverviewScreen
import com.shopl.sdg.ui.screen.component.AvatarScreen
import com.shopl.sdg.ui.screen.component.button.BoxButtonScreen

internal fun provideNavEntry(destination: SDGScene): NavEntry<Any> {
    return NavEntry(destination) {
        when (destination) {
            is SDGScene.Overview -> OverviewScreenRoute()
            is ComponentScene -> ComponentScreenRoute(destination)
            else -> {}
        }
    }
}

@Composable
private fun OverviewScreenRoute() {
    OverviewScreen()
}

@Composable
private fun ComponentScreenRoute(destination: ComponentScene) {
    when (destination) {
        is ComponentScene.Avatar -> {
            AvatarScreen()
        }
        is ComponentScene.Button -> {
            when(destination) {
                is ComponentScene.Button.BoxButton -> {
                    BoxButtonScreen()
                }
                else -> {}
            }
        }
        else -> {}
    }
}