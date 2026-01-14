package com.shopl.sdg.ui.screen

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.R
import com.shopl.sdg.component.badge.box.SDGBoxBadge
import com.shopl.sdg.component.badge.box.SDGBoxBadgeSize
import com.shopl.sdg.component.badge.box.SDGBoxBadgeType
import com.shopl.sdg.component.navigation.basic.SDGBasicNavi
import com.shopl.sdg.component.navigation.basic.SDGBasicNaviIconItem
import com.shopl.sdg.model.OverviewCardUiModel
import com.shopl.sdg.scene.SDGScene
import com.shopl.sdg.scene.componentScenes
import com.shopl.sdg.scene.foundationScenes
import com.shopl.sdg.scene.templateScenes
import com.shopl.sdg.ui.SDGScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList

/**
 * SDG Sample App - Overview
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3053&m=dev">Figma</a>
 */
@Composable
internal fun OverviewScreen(
    moveToScene: (SDGScene) -> Unit ,
) {
    val context = LocalContext.current
    val cardUiModels = remember {
        mutableListOf(
            OverviewCardUiModel(
                title = context.getString(R.string.overview_foundation_title),
                description = context.getString(R.string.overview_foundation_description),
                scenes = foundationScenes
            ),
            OverviewCardUiModel(
                title = context.getString(R.string.overview_component_title),
                description = context.getString(R.string.overview_component_description),
                scenes = componentScenes
            ),
            OverviewCardUiModel(
                title = context.getString(R.string.overview_template_title),
                description = context.getString(R.string.overview_template_description),
                scenes = templateScenes
            ),
        )
    }
    SDGScaffold(
        backgroundColor = SDGColor.Neutral900,
        topBar = {
            SDGBasicNavi(
                title = null,
                backgroundColor = SDGColor.Neutral900,
                leftIcon = SDGBasicNaviIconItem(
                    resId = R.drawable.ic_navi_drawer,
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
            items(cardUiModels) {
                Card(
                    uiModel = it,
                    onClickSceneButton = moveToScene
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
            resId = R.drawable.logo_sdg,
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
    uiModel: OverviewCardUiModel,
    onClickSceneButton: (SDGScene) -> Unit,
) = with(uiModel) {
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
            scenes.forEach { scene ->
                SDGBoxBadge(
                    size = SDGBoxBadgeSize.XSmall,
                    type = SDGBoxBadgeType.Solid,
                    label = scene.displayLabel,
                    labelColor = if(scene.implemented) SDGColor.Neutral400 else SDGColor.Neutral300,
                    backgroundColor = SDGColor.Neutral50,
                    enable = scene.implemented,
                    onClick = { onClickSceneButton(scene) }
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
        OverviewScreen(
            moveToScene = {}
        )
    }
}