package com.shopl.sdg.ui.screen.foundation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.shopl.sdg.ui.common.SDGSampleTypeTab
import com.shopl.sdg.ui.screen.foundation.model.TypographyUiModel
import com.shopl.sdg.ui.screen.foundation.model.toTypographyUiModel
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Foundation - Typography
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=23093-17051&m=dev">Figma</a>
 */

private enum class TypographType(val displayLabel: String) {
    PRETENDARD("Pretendard"),
}

private enum class TypographSpec(val displayLabel: String) {
    COMMON("Common"),
}

private val naviTypographies = persistentListOf(
    SDGTypography.NaviTitle.toTypographyUiModel(styleLabel = "Navi Title"),
)

private val titleTypographies = persistentListOf(
    SDGTypography.Title1SB.toTypographyUiModel(styleLabel = "Title 1 - SB"),
    SDGTypography.Title1R.toTypographyUiModel(styleLabel = "Title 1 - R"),
    SDGTypography.Title2SB.toTypographyUiModel(styleLabel = "Title 2 - SB"),
    SDGTypography.Title2R.toTypographyUiModel(styleLabel = "Title 2 - R"),
)

private val bodyTypographies = persistentListOf(
    SDGTypography.Body1SB.toTypographyUiModel(styleLabel = "Body 1 - SB"),
    SDGTypography.Body1R.toTypographyUiModel(styleLabel = "Body 1 - R"),
    SDGTypography.Body2SB.toTypographyUiModel(styleLabel = "Body 2 - SB"),
    SDGTypography.Body2R.toTypographyUiModel(styleLabel = "Body 2 - R"),
    SDGTypography.Body3SB.toTypographyUiModel(styleLabel = "Body 3 - SB"),
    SDGTypography.Body3R.toTypographyUiModel(styleLabel = "Body 3 - R"),
    SDGTypography.Body4SB.toTypographyUiModel(styleLabel = "Body 4 - SB"),
    SDGTypography.Body4R.toTypographyUiModel(styleLabel = "Body 4 - R"),
)

private val pointTypographies = persistentListOf(
    SDGTypography.Point1SB.toTypographyUiModel(styleLabel = "Point 1 - SB"),
    SDGTypography.Point1R.toTypographyUiModel(styleLabel = "Point 1 - R"),
    SDGTypography.Point2SB.toTypographyUiModel(styleLabel = "Point 2 - SB"),
    SDGTypography.Point2R.toTypographyUiModel(styleLabel = "Point 2 - R"),
)

private val specialTypographies = persistentListOf(
    SDGTypography.Special1SB.toTypographyUiModel(styleLabel = "Special 1 - SB"),
)

private const val RATIO_STYLE = 100F
private const val RATIO_SIZE = 40F
private const val RATIO_WEIGHT = 108F

@Composable
fun TypographScreen() {

    val types = persistentListOf(
        SDGSampleBaseTabItem(
            title = TypographType.PRETENDARD.displayLabel,
            item = TypographType.PRETENDARD
        ),
    )

    val specs = persistentListOf(
        SDGSampleBaseTabItem(
            title = TypographSpec.COMMON.displayLabel,
            item = TypographSpec.COMMON
        ),
    )

    SDGSampleBaseScaffold(
        name = FoundationScene.Typograph.displayLabel,
        description = stringResource(R.string.foundation_typography_description),
        bodyContent = {
            BodyContent(
                types = types,
                specs = specs
            )
        },
    )

}

@Composable
private fun BodyContent(
    types: ImmutableList<SDGSampleBaseTabItem<TypographType>>,
    specs: ImmutableList<SDGSampleBaseTabItem<TypographSpec>>,
) {

    var selectedType by remember { mutableStateOf(TypographType.PRETENDARD) }
    var selectedSpec by remember { mutableStateOf(TypographSpec.COMMON) }

    val tabModifier = Modifier.padding(
        start = SDGSpacing.Spacing16,
        top = SDGSpacing.Spacing16,
        end = SDGSpacing.Spacing16,
        bottom = SDGSpacing.Spacing12
    )

    SDGSampleTypeTab(
        modifier = tabModifier,
        tabs = types,
        selectedTabIndex = TypographType.entries.indexOf(selectedType),
        onTabClick = { index ->
            selectedType = TypographType.entries[index]
        }
    )

    HorizontalDivider(color = SDGColor.Neutral200)

    SDGSampleSpecTab(
        modifier = tabModifier,
        tabs = specs,
        selectedTabIndex = TypographSpec.entries.indexOf(selectedSpec),
        onTabClick = { index ->
            selectedSpec = TypographSpec.entries[index]
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
            ),
    ) {
        when (selectedType) {
            TypographType.PRETENDARD -> {
                when (selectedSpec) {
                    TypographSpec.COMMON -> {
                        TypographCommonContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun TypographCommonContent() {
    Column {
        TypographContentHeader()
        HorizontalDivider(color = SDGColor.Neutral200)
        TypographContentFrame(typographies = naviTypographies)
        HorizontalDivider(color = SDGColor.Neutral200)
        TypographContentFrame(typographies = titleTypographies)
        HorizontalDivider(color = SDGColor.Neutral200)
        TypographContentFrame(typographies = bodyTypographies)
        HorizontalDivider(color = SDGColor.Neutral200)
        TypographContentFrame(typographies = pointTypographies)
        HorizontalDivider(color = SDGColor.Neutral200)
        TypographContentFrame(typographies = specialTypographies)
    }
}

@Composable
private fun TypographContentHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SDGSpacing.Spacing16),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SDGText(
            modifier = Modifier.weight(RATIO_STYLE),
            text = "Style",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body3R
        )

        Spacer(modifier = Modifier.width(SDGSpacing.Spacing12))

        SDGText(
            modifier = Modifier.weight(RATIO_SIZE),
            text = "Size",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body3R
        )

        Spacer(modifier = Modifier.width(SDGSpacing.Spacing12))

        SDGText(
            modifier = Modifier.weight(RATIO_WEIGHT),
            text = "Weight",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body3R
        )

        Spacer(modifier = Modifier.width(SDGSpacing.Spacing24))
    }
}

@Composable
private fun TypographContentFrame(
    typographies: ImmutableList<TypographyUiModel>,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = SDGSpacing.Spacing16,
                top = SDGSpacing.Spacing16,
                end = SDGSpacing.Spacing8,
                bottom = SDGSpacing.Spacing16
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)
    ) {
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)
        ) {
            typographies.forEach { uiModel ->
                TypographContentRow(
                    styleLabel = uiModel.styleLabel,
                    sizeLabel = uiModel.sizeLabel,
                    weightLabel = uiModel.weightLabel,
                )
            }
        }
        SDGImage(
            resId = com.shopl.sdg_resource.R.drawable.ic_common_next,
            color = SDGColor.Neutral400,
        )
    }
}

@Composable
private fun TypographContentRow(
    styleLabel: String,
    sizeLabel: String,
    weightLabel: String,
    typography: SDGTypography = SDGTypography.Body1R,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SDGText(
            modifier = Modifier.weight(RATIO_STYLE),
            text = styleLabel,
            textColor = SDGColor.Neutral700,
            typography = typography
        )

        Spacer(modifier = Modifier.width(SDGSpacing.Spacing12))

        SDGText(
            modifier = Modifier.weight(RATIO_SIZE),
            text = sizeLabel,
            textColor = SDGColor.Neutral700,
            typography = typography
        )

        Spacer(modifier = Modifier.width(SDGSpacing.Spacing12))

        SDGText(
            modifier = Modifier.weight(RATIO_WEIGHT),
            text = weightLabel,
            textColor = SDGColor.Neutral700,
            typography = typography
        )
    }
}

@Preview
@Composable
private fun PreviewTypographScreen() {
    ShoplDesignGuideTheme {
        TypographScreen()
    }
}
