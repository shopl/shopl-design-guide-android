package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.scroll.SDGScrollTab
import com.shopl.sdg.component.tab.scroll.SDGScrollTabType
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.persistentListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoplDesignGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var selectedTabIndex by remember { mutableStateOf<Int?>(null) }
                    val tabTitles = persistentListOf("Tab 1", "Tab 2", "Tab 3", "Long Tab TitleLong Tab TitleLong Tab TitleLong Tab TitleLong Tab Title 4", "Tab 5", "Tab 6", "Another Tab", "Final Tab")
                    var selectedIndex by remember { mutableStateOf<Int?>(null) }
                    val tabTitles2 = persistentListOf("Tab 1", "Tab 2")

                    Column(modifier = Modifier.padding(innerPadding)) {
                        SDGScrollTab(
                            type = SDGScrollTabType.Line,
                            titles = tabTitles,
                            selectedIndex = selectedTabIndex,
                            maxItemWidth = 20.dp,
                            contentPadding = PaddingValues(horizontal = 50.dp),
                            onTabClick = { index ->
                                selectedTabIndex = index
                            }
                        )

                        SDGScrollTab(
                            type = SDGScrollTabType.Text,
                            titles = tabTitles2,
                            selectedIndex = selectedIndex,
                            onTabClick = { index ->
                                selectedIndex = index
                            }
                        )

                        SDGText(
                            text = "Selected Tab: ${selectedTabIndex?.let { tabTitles[it] }}",
                            textColor = SDGColor.Neutral700,
                            typography = SDGTypography.Body1R,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}