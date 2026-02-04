package com.shopl.sdg.ui.screen.foundation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.R
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.FoundationScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.common.SDGSampleSpecTab
import com.shopl.sdg.ui.screen.foundation.model.toColorUiModel
import com.shopl.sdg.ui.screen.foundation.ui.ColorsContent
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Foundation - Color
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22804-3506&m=dev">Figma</a>
 */

private enum class ColorSpec(val displayLabel: String) {
    NEUTRAL("Neutral"),
    BRAND("Brand"),
    POINT("Point"),
    SPECIAL("Special"),
}

internal const val COLOR_CHUNK_SIZE = 5

@Composable
internal fun ColorScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {

    val types = persistentListOf(
        SDGSampleBaseTabItem(
            title = ColorSpec.NEUTRAL.displayLabel,
            item = ColorSpec.NEUTRAL
        ),
        SDGSampleBaseTabItem(
            title = ColorSpec.BRAND.displayLabel,
            item = ColorSpec.BRAND
        ),
        SDGSampleBaseTabItem(
            title = ColorSpec.POINT.displayLabel,
            item = ColorSpec.POINT
        ),
        SDGSampleBaseTabItem(
            title = ColorSpec.SPECIAL.displayLabel,
            item = ColorSpec.SPECIAL
        ),
    )

    SDGSampleBaseScaffold(
        name = FoundationScene.Color.displayLabel,
        description = stringResource(R.string.foundation_color_description),
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
    specs: ImmutableList<SDGSampleBaseTabItem<ColorSpec>>,
) {

    var selectedSpec by remember { mutableStateOf(ColorSpec.NEUTRAL) }

    val tabModifier = Modifier.padding(
        start = SDGSpacing.Spacing16,
        top = SDGSpacing.Spacing16,
        end = SDGSpacing.Spacing16,
        bottom = SDGSpacing.Spacing12
    )

    SDGSampleSpecTab(
        modifier = tabModifier,
        tabs = specs,
        selectedTabIndex = ColorSpec.entries.indexOf(selectedSpec),
        onTabClick = { index ->
            selectedSpec = ColorSpec.entries[index]
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
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)
    ) {
        when (selectedSpec) {
            ColorSpec.NEUTRAL -> NeutralColorContent()
            ColorSpec.BRAND -> BradColorContent()
            ColorSpec.POINT -> PointColorContent()
            ColorSpec.SPECIAL -> SpecialColorContent()
        }
    }
}

@Composable
private fun NeutralColorContent() {
    val defaultColors = persistentListOf(
        SDGColor.Neutral900.toColorUiModel(displayLabel = "900"),
        SDGColor.Neutral700.toColorUiModel(displayLabel = "700"),
        SDGColor.Neutral600.toColorUiModel(displayLabel = "600"),
        SDGColor.Neutral500.toColorUiModel(displayLabel = "500"),
        SDGColor.Neutral400.toColorUiModel(displayLabel = "400"),
        SDGColor.Neutral350.toColorUiModel(displayLabel = "350"),
        SDGColor.Neutral300.toColorUiModel(displayLabel = "300"),
        SDGColor.Neutral250.toColorUiModel(displayLabel = "250"),
        SDGColor.Neutral200.toColorUiModel(displayLabel = "200"),
        SDGColor.Neutral150.toColorUiModel(displayLabel = "150"),
        SDGColor.Neutral100.toColorUiModel(displayLabel = "100"),
        SDGColor.Neutral50.toColorUiModel(displayLabel = "50"),
        SDGColor.Neutral0.toColorUiModel(displayLabel = "0"),
    )
    val alphaColors = persistentListOf(
        SDGColor.Neutral900_a10.toColorUiModel(displayLabel = "900-10"),
        SDGColor.Neutral700_a10.toColorUiModel(displayLabel = "700-10"),
        SDGColor.Neutral600_a10.toColorUiModel(displayLabel = "600-10"),
        SDGColor.Neutral500_a10.toColorUiModel(displayLabel = "500-10"),
        SDGColor.Neutral400_a10.toColorUiModel(displayLabel = "400-10"),
        SDGColor.Neutral350_a10.toColorUiModel(displayLabel = "350-10"),
        SDGColor.Neutral300_a10.toColorUiModel(displayLabel = "300-10"),
        SDGColor.Neutral250_a10.toColorUiModel(displayLabel = "250-10"),
        SDGColor.Neutral200_a10.toColorUiModel(displayLabel = "200-10"),
        SDGColor.Neutral150_a10.toColorUiModel(displayLabel = "150-10"),
        SDGColor.Neutral100_a10.toColorUiModel(displayLabel = "100-10"),
        SDGColor.Neutral50_a10.toColorUiModel(displayLabel = "50-10"),
        SDGColor.Neutral0_a10.toColorUiModel(displayLabel = "0-10"),
    )
    defaultColors.ColorsContent()
    alphaColors.ColorsContent()
}

@Composable
private fun BradColorContent() {
    val primaryColors = persistentListOf(
        SDGColor.Primary400.toColorUiModel(displayLabel = "400"),
        SDGColor.Primary300.toColorUiModel(displayLabel = "300"),
        SDGColor.Primary200.toColorUiModel(displayLabel = "200"),
        SDGColor.Primary50.toColorUiModel(displayLabel = "50"),
        SDGColor.Primary300_a10.toColorUiModel(displayLabel = "300-10"),
    )
    val secondaryColors = persistentListOf(
        SDGColor.Secondary400.toColorUiModel(displayLabel = "400"),
        SDGColor.Secondary300.toColorUiModel(displayLabel = "300"),
        SDGColor.Secondary200.toColorUiModel(displayLabel = "200"),
        SDGColor.Secondary50.toColorUiModel(displayLabel = "50"),
        SDGColor.Secondary400_a10.toColorUiModel(displayLabel = "400-10"),
        SDGColor.Secondary300_a10.toColorUiModel(displayLabel = "300-10"),
    )
    primaryColors.ColorsContent(title = "Primary")
    secondaryColors.ColorsContent(
        title = "Secondary",
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)
    )
}

@Composable
private fun PointColorContent() {
    val redColors = persistentListOf(
        SDGColor.Red400.toColorUiModel(displayLabel = "400"),
        SDGColor.Red350.toColorUiModel(displayLabel = "350"),
        SDGColor.Red300.toColorUiModel(displayLabel = "300"),
        SDGColor.Red50.toColorUiModel(displayLabel = "50"),
        SDGColor.Red300_a10.toColorUiModel(displayLabel = "300-10"),
    )
    val yellowColors = persistentListOf(
        SDGColor.YellowY.toColorUiModel(displayLabel = "Y"),
        SDGColor.YellowY_a10.toColorUiModel(displayLabel = "Y-10"),
    )
    val purpleColors = persistentListOf(
        SDGColor.PurpleP.toColorUiModel(displayLabel = "P"),
        SDGColor.PurpleP_a10.toColorUiModel(displayLabel = "P-10"),
    )
    val greenColors = persistentListOf(
        SDGColor.GreenG.toColorUiModel(displayLabel = "G"),
        SDGColor.GreenG_a10.toColorUiModel(displayLabel = "G-10"),
    )
    redColors.ColorsContent(title = "Red")
    yellowColors.ColorsContent(title = "Yellow")
    purpleColors.ColorsContent(title = "Purple")
    greenColors.ColorsContent(title = "Green")
}

@Composable
private fun SpecialColorContent() {
    val specialColors = persistentListOf(
        SDGColor.SpecialOR.toColorUiModel(displayLabel = "OR"),
        SDGColor.SpecialPK.toColorUiModel(displayLabel = "PK"),
        SDGColor.SpecialRP.toColorUiModel(displayLabel = "RP"),
        SDGColor.SpecialLe.toColorUiModel(displayLabel = "Le"),
        SDGColor.SpecialYG.toColorUiModel(displayLabel = "YG"),
        SDGColor.SpecialCG.toColorUiModel(displayLabel = "CG"),
    )
    specialColors.ColorsContent()
}

@Preview
@Composable
private fun PreviewColorScreen() {
    ShoplDesignGuideTheme {
        ColorScreen(
            onClickBack = {},
            onClickMenu = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNeutralColorContent() {
    ShoplDesignGuideTheme {
        Column(verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)) {
            NeutralColorContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBradColorContent() {
    ShoplDesignGuideTheme {
        Column(verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)) {
            BradColorContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPointColorContent() {
    ShoplDesignGuideTheme {
        Column(verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)) {
            PointColorContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSpecialColorContent() {
    ShoplDesignGuideTheme {
        Column(verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40)) {
            SpecialColorContent()
        }
    }
}
