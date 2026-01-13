package com.shopl.sdg.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.badge.box.SDGBoxBadge
import com.shopl.sdg.component.badge.box.SDGBoxBadgeSize
import com.shopl.sdg.component.badge.box.SDGBoxBadgeType
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Overview
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3053&m=dev">Figma</a>
 */
@Composable
internal fun OverviewScreen(

) {
    SDGScaffold(
        backgroundColor = SDGColor.Neutral900,
        topBar = {
            SDGBasicNavi(
                title = null,
                backgroundColor = SDGColor.Neutral900,
                leftIcon = SDGBasicNaviIconItem(
                    resId = com.shopl.sdg.R.drawable.ic_navi_drawer,
                    onClick = {}
                ),
                rightIcons = null
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SDGColor.Neutral0),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(
                bottom = SDGSpacing.Spacing40
            ),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16)
        ) {
            item {
                Header()
            }
            item {
                Card(
                    title = "Foundation",
                    description = "일관된 레이아웃과 그에 따른 사용자 경험을 만드는 데 필수적인 시각적 요소입니다.",
                    items = persistentListOf(
                        "Color",
                        "Corner Radius",
                        "Iconography",
                        "Spacing",
                        "Typography",
                    )
                )
            }
            item {
                BottomInfo()
            }
        }
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SDGColor.Neutral900)
            .padding(
                start = SDGSpacing.Spacing16,
                top = SDGSpacing.Spacing32,
                end = SDGSpacing.Spacing16,
                bottom = SDGSpacing.Spacing60
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)
    ) {
        SDGImage(
            modifier = Modifier.fillMaxWidth(),
            resId = com.shopl.sdg.R.drawable.logo_sdg,
            color = null
        )
        SDGText(
            text = "Shopl Design Guide",
            textColor = SDGColor.Neutral0,
            typography = SDGTypography.Title2SB
        )
    }
}

@Composable
private fun Card(
    title: String,
    description: String,
    items: PersistentList<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16
            )
            .border(
                width = 1.dp,
                color = SDGColor.Neutral200,
                shape = SDGCornerRadius.BoxRadius.Radius8
            )
            .padding(
                vertical = SDGSpacing.Spacing20,
                horizontal = SDGSpacing.Spacing16,
            ),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)
        ) {
            SDGText(
                text = title,
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Point2SB
            )
            SDGText(
                text = description,
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1R
            )
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
            horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)
        ) {
            items.forEach {
                SDGBoxBadge(
                    size = SDGBoxBadgeSize.XSmall,
                    type = SDGBoxBadgeType.Solid,
                    label = it,
                    labelColor = SDGColor.Neutral400,
                    backgroundColor = SDGColor.Neutral50,
                    enable = true,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun BottomInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = SDGSpacing.Spacing16
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SDGText(
            text = "Shopl App Design System",
            textColor = SDGColor.Neutral300,
            typography = SDGTypography.Body4R
        )
        SDGText(
            text = "Sample App Version : 1.0.0",
            textColor = SDGColor.Neutral300,
            typography = SDGTypography.Body4R
        )
        SDGText(
            // TODO : PublishingConfig 모듈 변경
            text = "SDG Version : 2.3.0",
            textColor = SDGColor.Neutral300,
            typography = SDGTypography.Body4R
        )
    }
}

@Preview
@Composable
private fun PreviewOverviewScreen() {
    ShoplDesignGuideTheme {
        OverviewScreen()
    }
}