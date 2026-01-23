package com.shopl.sdg.ui.screen.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.R
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.FoundationScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.common.SDGSampleSpecTab
import com.shopl.sdg.ui.screen.foundation.model.toIconographyUiModel
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Sample App - Foundation - Iconography
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=23093-16751&m=dev">Figma</a>
 */

private enum class IconographySpec(val displayLabel: String) {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large"),
}

private const val ICON_BOX_ALPHA = 0.2f
private const val ICON_BOX_COLOR = 0xFF9747FF

private val smallIconographyUiModels = persistentListOf(
    14.dp.toIconographyUiModel(displayLabel = "14*14"),
    18.dp.toIconographyUiModel(displayLabel = "18*18"),
)

private val mediumIconographyUiModels = persistentListOf(
    20.dp.toIconographyUiModel(displayLabel = "20*20"),
    24.dp.toIconographyUiModel(displayLabel = "24*24"),
)

private val largeIconographyUiModels = persistentListOf(
    36.dp.toIconographyUiModel(displayLabel = "36*36"),
    40.dp.toIconographyUiModel(displayLabel = "40*40"),
)


@Composable
internal fun IconographyScreen() {

    val types = persistentListOf(
        SDGSampleBaseTabItem(
            title = IconographySpec.SMALL.displayLabel,
            item = IconographySpec.SMALL
        ),
        SDGSampleBaseTabItem(
            title = IconographySpec.MEDIUM.displayLabel,
            item = IconographySpec.MEDIUM
        ),
        SDGSampleBaseTabItem(
            title = IconographySpec.LARGE.displayLabel,
            item = IconographySpec.LARGE
        ),
    )

    SDGSampleBaseScaffold(
        name = FoundationScene.Iconography.displayLabel,
        description = stringResource(R.string.foundation_iconography_description),
        bodyContent = {
            BodyContent(
                specs = types
            )
        },
    )

}

@Composable
private fun BodyContent(
    specs: ImmutableList<SDGSampleBaseTabItem<IconographySpec>>,
) {

    var selectedSpec by remember { mutableStateOf(IconographySpec.SMALL) }

    val tabModifier = Modifier.padding(
        start = SDGSpacing.Spacing16,
        top = SDGSpacing.Spacing16,
        end = SDGSpacing.Spacing16,
        bottom = SDGSpacing.Spacing12
    )

    SDGSampleSpecTab(
        modifier = tabModifier,
        tabs = specs,
        selectedTabIndex = IconographySpec.entries.indexOf(selectedSpec),
        onTabClick = { index ->
            selectedSpec = IconographySpec.entries[index]
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
        val uiModels = when (selectedSpec) {
            IconographySpec.SMALL -> smallIconographyUiModels
            IconographySpec.MEDIUM -> mediumIconographyUiModels
            IconographySpec.LARGE -> largeIconographyUiModels
        }
        uiModels.forEach { uiModel ->
            IconographyContent(
                title = uiModel.displayLabel,
                size = uiModel.size
            )
        }
    }
}

@Composable
private fun IconographyContent(
    title: String,
    size: Dp
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12),
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body2R
        )
        Box(
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
            contentAlignment = Alignment.Center
        ) {
            Spacer(
                modifier = Modifier
                    .alpha(ICON_BOX_ALPHA)
                    .size(size)
                    .background(
                        color = Color(ICON_BOX_COLOR),
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewIconographyScreen() {
    ShoplDesignGuideTheme {
        IconographyScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSmallContent() {
    ShoplDesignGuideTheme {
        Column {
            smallIconographyUiModels.forEach { uiModel ->
                IconographyContent(
                    title = uiModel.displayLabel,
                    size = uiModel.size
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMediumContent() {
    ShoplDesignGuideTheme {
        Column {
            mediumIconographyUiModels.forEach { uiModel ->
                IconographyContent(
                    title = uiModel.displayLabel,
                    size = uiModel.size
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLargeContent() {
    ShoplDesignGuideTheme {
        Column {
            largeIconographyUiModels.forEach { uiModel ->
                IconographyContent(
                    title = uiModel.displayLabel,
                    size = uiModel.size
                )
            }
        }
    }
}
