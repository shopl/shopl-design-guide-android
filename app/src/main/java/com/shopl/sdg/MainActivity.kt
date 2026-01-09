package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.shopl.sdg.template.list_header.icon.IconType
import com.shopl.sdg.template.list_header.icon.SDGIconHeader
import com.shopl.sdg.template.list_header.icon.SDGIconHeaderIcon
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoplDesignGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SDGColor.GreenG)
                            .padding(innerPadding),
                    ) {
                        SDGIconHeader(
                            label = "메일",
                            count = "12",
                            iconType = IconType.ONLY_ICON,
                            rightIcons = persistentListOf(
                                SDGIconHeaderIcon(R.drawable.ic_common_search, SDGColor.Neutral700),
                                SDGIconHeaderIcon(R.drawable.ic_common_company, SDGColor.Neutral700),
                                SDGIconHeaderIcon(R.drawable.ic_common_staff, SDGColor.Neutral700)
                            ),
                        )

                        SDGIconHeader(
                            label = "메일",
                            count = "12",
                            iconType = IconType.WITH_BOX,
                            rightIcons = persistentListOf(
                                SDGIconHeaderIcon(
                                    resId = R.drawable.ic_common_company,
                                    color = SDGColor.Primary300,
                                    onClick = {}
                                ),
                                SDGIconHeaderIcon(
                                    resId = R.drawable.ic_common_company,
                                    color = SDGColor.Primary300,
                                    onClick = {}
                                ),
                                SDGIconHeaderIcon(
                                    resId = R.drawable.ic_common_company,
                                    color = SDGColor.Primary300,
                                    onClick = {}
                                )
                            )
                        )

                        SDGIconHeader(
                            label = "메일",
                            count = "12",
                            iconType = IconType.ONLY_ICON,
                            rightIcons = persistentListOf(
                                SDGIconHeaderIcon(R.drawable.ic_common_search, SDGColor.Neutral700),
                                SDGIconHeaderIcon(R.drawable.ic_common_company, SDGColor.Neutral700),
                                SDGIconHeaderIcon(R.drawable.ic_common_staff, SDGColor.Neutral700)
                            ),
                        )

                        SDGIconHeader(
                            label = "메일",
                            count = "12",
                            iconType = IconType.ONLY_ICON,
                            rightIcons = persistentListOf(
                                SDGIconHeaderIcon(
                                    resId = R.drawable.ic_common_company,
                                    color = SDGColor.Primary300,
                                    onClick = {}
                                ),
                                SDGIconHeaderIcon(
                                    resId = R.drawable.ic_common_company,
                                    color = SDGColor.Primary300,
                                    onClick = {}
                                ),
                                SDGIconHeaderIcon(
                                    resId = R.drawable.ic_common_company,
                                    color = SDGColor.Primary300,
                                    onClick = {}
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}
