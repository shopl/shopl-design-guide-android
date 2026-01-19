package com.shopl.sdg.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.R
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

@Composable
internal fun SDGSampleBaseScaffold(
    name: String,
    description: String,
    bodyContent: @Composable () -> Unit,
    usageGuideLinesContent: @Composable (() -> Unit)? = null,
) {
    SDGScaffold(
        backgroundColor = SDGColor.Neutral0,
        topBar = {
            SDGBasicNavi(
                title = null,
                backgroundColor = SDGColor.Neutral0,
                leftIcon = SDGBasicNaviIconItem(
                    resId = R.drawable.ic_navi_drawer,
                    color = SDGColor.Neutral700,
                    onClick = {
                        // TODO : Show Menu
                    }
                ),
                rightIcons = null
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    top = SDGSpacing.Spacing10,
                    bottom = SDGSpacing.Spacing40
                ),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16)
        ) {
            HeaderContent(
                name = name,
                description = description
            )

            HorizontalDivider(
                color = SDGColor.Neutral700
            )

            bodyContent()

            if(usageGuideLinesContent != null) {
                HorizontalDivider(
                    color = SDGColor.Neutral200
                )

                Column(
                    modifier = Modifier
                        .padding(
                            top = SDGSpacing.Spacing4,
                            start = SDGSpacing.Spacing16,
                            end = SDGSpacing.Spacing16,
                        )
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)
                ) {
                    SDGText(
                        text = "Usage Guidelines",
                        textColor = SDGColor.Neutral350,
                        typography = SDGTypography.Body3SB
                    )
                    usageGuideLinesContent()
                }
            }
        }
    }
}

@Composable
private fun HeaderContent(
    name: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SDGSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)
    ) {
        SDGText(
            text = name,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Point1SB
        )
        SDGText(
            text = description,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body2R
        )
    }
}

@Preview
@Composable
private fun PreviewSDGSampleBaseScaffold() {
    ShoplDesignGuideTheme {
        SDGSampleBaseScaffold(
            name = "SDG Component/Template Name",
            description = "SDG Component/Template Description",
            bodyContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(SDGColor.Neutral150)
                ) {
                    SDGText(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Sample Body Content",
                        textColor = SDGColor.Neutral700,
                        typography = SDGTypography.Title2R
                    )
                }
            },
            usageGuideLinesContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(SDGColor.Neutral150)
                ) {
                    SDGText(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Sample Usage GuideLines Content(Optional)",
                        textColor = SDGColor.Neutral700,
                        typography = SDGTypography.Body3R
                    )
                }
            }
        )
    }
}