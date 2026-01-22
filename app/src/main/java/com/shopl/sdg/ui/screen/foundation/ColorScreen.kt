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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.FoundationScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.common.SDGSampleTypeTab
import com.shopl.sdg.ui.screen.foundation.ui.ColorsContent
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

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
internal fun ColorScreen() {

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
        description = "샤플 앱의 모든 요소에 적용되며, 일관된/뚜렷한/계층적인 컬러 사용으로 서비스의 아이덴티티 및 브랜드 경험을 만들어 줄 수 있는 주요 요소",
        bodyContent = {
            BodyContent(
                types = types
            )
        },
    )

}

@Composable
private fun BodyContent(
    types: PersistentList<SDGSampleBaseTabItem<ColorSpec>>,
) {

    var selectedType by remember { mutableStateOf(ColorSpec.NEUTRAL) }

    val tabModifier = Modifier.padding(
        start = SDGSpacing.Spacing16,
        top = SDGSpacing.Spacing16,
        end = SDGSpacing.Spacing16,
        bottom = SDGSpacing.Spacing12
    )

    SDGSampleTypeTab(
        modifier = tabModifier,
        tabs = types,
        selectedTabIndex = ColorSpec.entries.indexOf(selectedType),
        onTabClick = { index ->
            selectedType = ColorSpec.entries[index]
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
        when (selectedType) {
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
        SDGColor.Neutral900 to "900",
        SDGColor.Neutral700 to "700",
        SDGColor.Neutral600 to "600",
        SDGColor.Neutral500 to "500",
        SDGColor.Neutral400 to "400",
        SDGColor.Neutral350 to "350",
        SDGColor.Neutral300 to "300",
        SDGColor.Neutral250 to "250",
        SDGColor.Neutral200 to "200",
        SDGColor.Neutral150 to "150",
        SDGColor.Neutral100 to "100",
        SDGColor.Neutral50 to "50",
        SDGColor.Neutral0 to "0",
    )
    val alphaColors = persistentListOf(
        SDGColor.Neutral900_a10 to "900-10",
        SDGColor.Neutral700_a10 to "700-10",
        SDGColor.Neutral600_a10 to "600-10",
        SDGColor.Neutral500_a10 to "500-10",
        SDGColor.Neutral400_a10 to "400-10",
        SDGColor.Neutral350_a10 to "350-10",
        SDGColor.Neutral300_a10 to "300-10",
        SDGColor.Neutral250_a10 to "250-10",
        SDGColor.Neutral200_a10 to "200-10",
        SDGColor.Neutral150_a10 to "150-10",
        SDGColor.Neutral100_a10 to "100-10",
        SDGColor.Neutral50_a10 to "50-10",
        SDGColor.Neutral0_a10 to "0-10",
    )
    defaultColors.chunked(COLOR_CHUNK_SIZE).forEach { chunkedColors ->
        ColorsContent(
            colors = chunkedColors.toPersistentList()
        )
    }
    alphaColors.chunked(COLOR_CHUNK_SIZE).forEach { chunkedColors ->
        ColorsContent(
            colors = chunkedColors.toPersistentList()
        )
    }
}

@Composable
private fun BradColorContent() {
    val primaryColors = persistentListOf(
        SDGColor.Primary400 to "400",
        SDGColor.Primary300 to "300",
        SDGColor.Primary200 to "200",
        SDGColor.Primary50 to "50",
        SDGColor.Primary300_a10 to "300-10",
    )
    val secondaryColors = persistentListOf(
        SDGColor.Secondary400 to "400",
        SDGColor.Secondary300 to "300",
        SDGColor.Secondary200 to "200",
        SDGColor.Secondary50 to "50",
        SDGColor.Secondary400_a10 to "400-10",
        SDGColor.Secondary300_a10 to "300-10",
    )
    primaryColors.chunked(COLOR_CHUNK_SIZE).forEachIndexed { index, chunkedColor ->
        if (index == 0) {
            ColorsContent(
                title = "Primary",
                colors = chunkedColor.toPersistentList()
            )
        } else {
            ColorsContent(
                colors = chunkedColor.toPersistentList()
            )
        }
    }
    secondaryColors.chunked(COLOR_CHUNK_SIZE).forEachIndexed { index, chunkedColor ->
        if (index == 0) {
            ColorsContent(
                title = "Secondary",
                colors = chunkedColor.toPersistentList()
            )
        } else {
            ColorsContent(
                colors = chunkedColor.toPersistentList()
            )
        }
    }
}

@Composable
private fun PointColorContent() {
    val redColors = persistentListOf(
        SDGColor.Red400 to "400",
        SDGColor.Red350 to "350",
        SDGColor.Red300 to "300",
        SDGColor.Red50 to "50",
        SDGColor.Red300_a10 to "300-10",
    )
    val yellowColors = persistentListOf(
        SDGColor.YellowY to "Y",
        SDGColor.YellowY_a10 to "Y-10",
    )
    val purpleColors = persistentListOf(
        SDGColor.PurpleP to "P",
        SDGColor.PurpleP_a10 to "P-10",
    )
    val greenColors = persistentListOf(
        SDGColor.GreenG to "G",
        SDGColor.GreenG_a10 to "G-10",
    )
    redColors.chunked(COLOR_CHUNK_SIZE).forEachIndexed { index, chunkedColor ->
        if (index == 0) {
            ColorsContent(
                title = "Red",
                colors = chunkedColor.toPersistentList()
            )
        } else {
            ColorsContent(
                colors = chunkedColor.toPersistentList()
            )
        }
    }
    yellowColors.chunked(COLOR_CHUNK_SIZE).forEachIndexed { index, chunkedColor ->
        if (index == 0) {
            ColorsContent(
                title = "Yellow",
                colors = chunkedColor.toPersistentList()
            )
        } else {
            ColorsContent(
                colors = chunkedColor.toPersistentList()
            )
        }
    }
    purpleColors.chunked(COLOR_CHUNK_SIZE).forEachIndexed { index, chunkedColor ->
        if (index == 0) {
            ColorsContent(
                title = "Purple",
                colors = chunkedColor.toPersistentList()
            )
        } else {
            ColorsContent(
                colors = chunkedColor.toPersistentList()
            )
        }
    }
    greenColors.chunked(COLOR_CHUNK_SIZE).forEachIndexed { index, chunkedColor ->
        if (index == 0) {
            ColorsContent(
                title = "Green",
                colors = chunkedColor.toPersistentList()
            )
        } else {
            ColorsContent(
                colors = chunkedColor.toPersistentList()
            )
        }
    }
}

@Composable
private fun SpecialColorContent() {
    val specialColors = persistentListOf(
        SDGColor.SpecialOR to "OR",
        SDGColor.SpecialPK to "PK",
        SDGColor.SpecialRP to "RP",
        SDGColor.SpecialLe to "Le",
        SDGColor.SpecialYG to "YG",
        SDGColor.SpecialCG to "CG",
    )
    specialColors.chunked(COLOR_CHUNK_SIZE).forEachIndexed { index, chunkedColor ->
        ColorsContent(
            colors = chunkedColor.toPersistentList()
        )
    }
}

@Preview
@Composable
private fun PreviewColorScreen() {
    ShoplDesignGuideTheme {
        ColorScreen()
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
