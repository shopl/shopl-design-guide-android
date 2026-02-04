package com.shopl.sdg.ui.screen.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.R
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.FoundationScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.common.SDGSampleSpecTab
import com.shopl.sdg.ui.screen.foundation.model.SpacingUiModel
import com.shopl.sdg.ui.screen.foundation.model.toSpacingUiModel
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Foundation - Spacing
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3506&m=dev">Figma</a>
 */

private enum class SpacingSpec(val displayLabel: String) {
    COMMON("Common"),
    SPECIAL("Special"),
}

private const val SPACE_BOX_ALPHA = 0.2f
private const val SPACE_BOX_COLOR = 0xFF9747FF

private val commonSpacings = persistentListOf(
    SDGSpacing.Spacing2.toSpacingUiModel(displayLabel = "2"),
    SDGSpacing.Spacing4.toSpacingUiModel(displayLabel = "4"),
    SDGSpacing.Spacing6.toSpacingUiModel(displayLabel = "6"),
    SDGSpacing.Spacing8.toSpacingUiModel(displayLabel = "8"),
    SDGSpacing.Spacing10.toSpacingUiModel(displayLabel = "10"),
    SDGSpacing.Spacing12.toSpacingUiModel(displayLabel = "12"),
    SDGSpacing.Spacing16.toSpacingUiModel(displayLabel = "16"),
    SDGSpacing.Spacing20.toSpacingUiModel(displayLabel = "20"),
    SDGSpacing.Spacing24.toSpacingUiModel(displayLabel = "24"),
    SDGSpacing.Spacing40.toSpacingUiModel(displayLabel = "40"),
)

private val specialSpacings = persistentListOf(
    SDGSpacing.Spacing3.toSpacingUiModel(displayLabel = "3"),
    SDGSpacing.Spacing5.toSpacingUiModel(displayLabel = "5"),
    SDGSpacing.Spacing11.toSpacingUiModel(displayLabel = "11"),
    SDGSpacing.Spacing18.toSpacingUiModel(displayLabel = "18"),
    SDGSpacing.Spacing28.toSpacingUiModel(displayLabel = "28"),
    SDGSpacing.Spacing32.toSpacingUiModel(displayLabel = "32"),
    SDGSpacing.Spacing60.toSpacingUiModel(displayLabel = "60"),
    SDGSpacing.Spacing100.toSpacingUiModel(displayLabel = "100"),
    SDGSpacing.Spacing104.toSpacingUiModel(displayLabel = "104"),
)

@Composable
internal fun SpacingScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {

    val types = persistentListOf(
        SDGSampleBaseTabItem(
            title = SpacingSpec.COMMON.displayLabel,
            item = SpacingSpec.COMMON
        ),
        SDGSampleBaseTabItem(
            title = SpacingSpec.SPECIAL.displayLabel,
            item = SpacingSpec.SPECIAL
        ),
    )

    SDGSampleBaseScaffold(
        name = FoundationScene.Spacing.displayLabel,
        description = stringResource(R.string.foundation_spacing_description),
        bodyContent = {
            BodyContent(
                specs = types
            )
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )

}

@Composable
private fun BodyContent(
    specs: ImmutableList<SDGSampleBaseTabItem<SpacingSpec>>,
) {

    var selectedSpec by remember { mutableStateOf(SpacingSpec.COMMON) }

    val tabModifier = Modifier.padding(
        start = SDGSpacing.Spacing16,
        top = SDGSpacing.Spacing16,
        end = SDGSpacing.Spacing16,
        bottom = SDGSpacing.Spacing12
    )

    SDGSampleSpecTab(
        modifier = tabModifier,
        tabs = specs,
        selectedTabIndex = SpacingSpec.entries.indexOf(selectedSpec),
        onTabClick = { index ->
            selectedSpec = SpacingSpec.entries[index]
        }
    )

    Column(
        modifier = Modifier
            .padding(horizontal = SDGSpacing.Spacing16)
            .fillMaxWidth()
            .clip(SDGCornerRadius.BoxRadius.Radius8)
            .border(
                width = 1.dp,
                color = SDGColor.Neutral200,
                shape = SDGCornerRadius.BoxRadius.Radius8
            )
            .padding(SDGSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)
    ) {
        when (selectedSpec) {
            SpacingSpec.COMMON -> {
                SpacingsContent(
                    title = stringResource(R.string.foundation_spacing_common_description),
                    uiModels = commonSpacings
                )
            }

            SpacingSpec.SPECIAL -> {
                SpacingsContent(
                    title = stringResource(R.string.foundation_spacing_special_description),
                    uiModels = specialSpacings
                )
            }
        }
    }
}

@Composable
private fun SpacingsContent(
    title: String,
    uiModels: ImmutableList<SpacingUiModel>
) {
    SDGText(
        text = title,
        textColor = SDGColor.Neutral700,
        typography = SDGTypography.Body2R
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral50,
                shape = SDGCornerRadius.BoxRadius.Radius8
            )
            .padding(
                vertical = SDGSpacing.Spacing32,
                horizontal = SDGSpacing.Spacing16
            ),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiModels.forEach { uiModel ->
            SpacingContent(
                uiModel = uiModel
            )
        }
    }
}

@Composable
private fun SpacingContent(
    uiModel: SpacingUiModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier
                .alpha(SPACE_BOX_ALPHA)
                .size(uiModel.size)
                .background(
                    color = Color(SPACE_BOX_COLOR),
                )
        )
        SDGText(
            text = uiModel.displayLabel,
            textColor = SDGColor.Neutral400,
            typography = SDGTypography.Body3R
        )
    }
}

@Preview
@Composable
private fun PreviewSpacingScreen() {
    ShoplDesignGuideTheme {
        SpacingScreen(
            onClickBack = {},
            onClickMenu = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCommonSpacingsContent() {
    ShoplDesignGuideTheme {
        Column {
            SpacingsContent(
                title = stringResource(R.string.foundation_spacing_common_description),
                uiModels = commonSpacings
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSpecialSpacingsContent() {
    ShoplDesignGuideTheme {
        Column {
            SpacingsContent(
                title = stringResource(R.string.foundation_spacing_special_description),
                uiModels = specialSpacings
            )
        }
    }
}
