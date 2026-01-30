package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.shopl.sdg.scene.SDGScene
import com.shopl.sdg.ui.setEdgeToEdgeConfig
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme

private const val ANIMATION_DURATION = 300

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeConfig()
        setContent {
            ShoplDesignGuideTheme {
                val backStack = remember { mutableStateListOf<Any>(SDGScene.Overview) }

                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = { key ->
                        val scene = key as? SDGScene ?: error("Unknown key: $key")
                        NavEntry(scene) {
                            SDGRouteWrapper(isDarkIcon = scene.isDarkIcon) {
                                scene.Screen(
                                    moveToScene = { next -> backStack.add(next) }
                                )
                            }
                        }
                    },
                    transitionSpec = {
                        // Slide in from right when navigating forward
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    },
                    popTransitionSpec = {
                        // Slide in from left when navigating back
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    },
                    predictivePopTransitionSpec = {
                        // Slide in from left when navigating back
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) togetherWith slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(ANIMATION_DURATION)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SDGRouteWrapper(
    isDarkIcon: Boolean = true,
    content: @Composable () -> Unit
) {
    val activity = LocalActivity.current

    SideEffect {
        activity?.window?.let { window ->
            val view = window.decorView
            val windowInsetsController = WindowCompat.getInsetsController(window, view)
            windowInsetsController.isAppearanceLightStatusBars = isDarkIcon
        }
    }
    content()
}
