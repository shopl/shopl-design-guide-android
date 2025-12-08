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
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.shopl.sdg.navigation.ComponentDestination
import com.shopl.sdg.navigation.SDGDestination
import com.shopl.sdg.tmp.AvatarScreen
import com.shopl.sdg.tmp.BoxBadgeScreen
import com.shopl.sdg.tmp.CapsuleBadgeScreen
import com.shopl.sdg.tmp.HomeScreen
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg.util.nav3PopupEnter
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
    val backStack = rememberNavBackStack(SDGDestination.Home)

    NavDisplay(
        modifier = Modifier
            .padding(innerPadding)
            .background(SDGColor.GreenG),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<SDGDestination.Home> {
                HomeScreen(
                    onClick = { backStack.add(ComponentDestination.Avatar as ComponentDestination as SDGDestination) }
                )
            }

            entry<ComponentDestination.Avatar>(
                metadata = nav3PopupEnter()
            ) {
                AvatarScreen(
                    onClick = { backStack.add(ComponentDestination.Badge.CapsuleBadge as ComponentDestination.Badge as SDGDestination) }
                )
            }

            entry<ComponentDestination.Badge.CapsuleBadge> {
                CapsuleBadgeScreen(
                    onClick = { backStack.add(ComponentDestination.Badge.BoxBadge as ComponentDestination.Badge as SDGDestination) }
                )
            }

            entry<ComponentDestination.Badge.BoxBadge> {
                BoxBadgeScreen(
                    onClick = { backStack.add(ComponentDestination.Avatar as ComponentDestination as SDGDestination) }
                )
            }
        },
    )
}