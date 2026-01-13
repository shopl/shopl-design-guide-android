package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.shopl.sdg.navigation.SDGDestination
import com.shopl.sdg.screen.OverviewScreen
import com.shopl.sdg.ui.setEdgeToEdgeConfig
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeConfig()
        setContent {
            ShoplDesignGuideTheme {
                val backStack = remember { mutableStateListOf<Any>(SDGDestination.Overview) }

                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = { key ->
                        when (key) {
                            is SDGDestination.Overview -> NavEntry(key) {
                                OverviewScreen()
                            }

                            else -> {
                                error("Unknown route: $key")
                            }
                        }
                    }
                )
            }
        }
    }
}