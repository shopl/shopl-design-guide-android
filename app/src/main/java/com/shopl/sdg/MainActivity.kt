package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                var lastPoppedWasPopup by remember { mutableStateOf(false) }

                NavDisplay(
                    backStack = backStack,
                    onBack = {
                        val topScene = backStack.lastOrNull() as? SDGScene
                        lastPoppedWasPopup = topScene?.isPopup == true
                        backStack.removeLastOrNull()
                    },
                    entryProvider = { key ->
                        val scene = key as? SDGScene ?: error("Unknown key: $key")
                        NavEntry(scene) {
                            SDGRouteWrapper(isDarkIcon = scene.isDarkIcon) {
                                scene.Screen(
                                    moveToScene = { next -> backStack.add(next) },
                                    backToScene = {
                                        lastPoppedWasPopup = scene.isPopup
                                        backStack.removeLastOrNull()
                                    }
                                )
                            }
                        }
                    },
                    transitionSpec = {
                        val targetScene = backStack.lastOrNull() as? SDGScene
                        if (targetScene?.isPopup == true) {
                            slideInVertically(
                                initialOffsetY = { it },
                                animationSpec = tween(ANIMATION_DURATION)
                            ) + fadeIn(
                                animationSpec = tween(ANIMATION_DURATION)
                            ) togetherWith fadeOut(
                                animationSpec = tween(ANIMATION_DURATION)
                            )
                        } else {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(ANIMATION_DURATION)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(ANIMATION_DURATION)
                            )
                        }
                    },
                    popTransitionSpec = {
                        if (lastPoppedWasPopup) {
                            slideInHorizontally(initialOffsetX = { 0 }) togetherWith slideOutVertically(
                                targetOffsetY = { it },
                                animationSpec = tween(ANIMATION_DURATION)
                            ) + fadeOut()
                        } else {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(ANIMATION_DURATION)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(ANIMATION_DURATION)
                            )
                        }
                    },
                    predictivePopTransitionSpec = {
                        if (lastPoppedWasPopup) {
                            val enter = fadeIn(animationSpec = tween(ANIMATION_DURATION))
                            val exit = slideOutVertically(
                                targetOffsetY = { it },
                                animationSpec = tween(ANIMATION_DURATION)
                            ) + fadeOut()
                            enter togetherWith exit
                        } else {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(ANIMATION_DURATION)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(ANIMATION_DURATION)
                            )
                        }
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
