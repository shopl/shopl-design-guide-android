package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.ui.NavDisplay
import com.shopl.sdg.navigation.provideNavEntry
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
                        when (key) {
                            is SDGScene -> provideNavEntry(
                                destination = key,
                                moveToScene = {
                                    backStack.add(it)
                                }
                            )

                            else -> error("Unknown route: $key")
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
