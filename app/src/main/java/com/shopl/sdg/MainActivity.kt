package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.shopl.sdg.navigation.ComponentDestination
import com.shopl.sdg.navigation.SDGDestination
import com.shopl.sdg.tmp.AvatarScreen
import com.shopl.sdg.tmp.BoxBadgeScreen
import com.shopl.sdg.tmp.CapsuleBadgeScreen
import com.shopl.sdg.tmp.EmptyScreen
import com.shopl.sdg.tmp.HomeScreen
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoplDesignGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationBasic(innerPadding)
                }
            }
        }
    }
}

@Composable
private fun NavigationBasic(innerPadding: PaddingValues) {
    val backStack = remember { mutableStateListOf<SDGDestination>(SDGDestination.Home) }
    NavDisplay(
        modifier = Modifier
            .padding(innerPadding)
            .background(SDGColor.GreenG),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                SDGDestination.Home -> NavEntry(key) {
                    HomeScreen(
                        onClick = { backStack.add(ComponentDestination.Avatar as ComponentDestination as SDGDestination) }
                    )
                }

                ComponentDestination.Avatar -> NavEntry(key) {
                    AvatarScreen(
                        onClick = { backStack.add(ComponentDestination.Badge.CapsuleBadge as ComponentDestination.Badge as SDGDestination) }
                    )
                }

                ComponentDestination.Badge.CapsuleBadge -> NavEntry(key) {
                    CapsuleBadgeScreen(
                        onClick = { backStack.add(ComponentDestination.Badge.BoxBadge as ComponentDestination.Badge as SDGDestination) }
                    )
                }

                ComponentDestination.Badge.BoxBadge -> NavEntry(key) {
                    BoxBadgeScreen(
                        onClick = { backStack.add(ComponentDestination.Avatar as ComponentDestination as SDGDestination) }
                    )
                }

                else -> NavEntry(key) {
                    EmptyScreen(
                        onClick = { backStack.add(ComponentDestination.Avatar as ComponentDestination as SDGDestination) }
                    )
                }
            }
        }
    )
}