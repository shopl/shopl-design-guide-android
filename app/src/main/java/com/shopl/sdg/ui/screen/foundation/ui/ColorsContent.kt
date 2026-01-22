package com.shopl.sdg.ui.screen.foundation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.ui.screen.foundation.COLOR_CHUNK_SIZE
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ColorsContent(
    title: String,
    colors: PersistentList<Pair<Color, String>>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body2R
        )
        ColorsContent(
            colors = colors
        )
    }
}

@Composable
internal fun ColorsContent(
    colors: PersistentList<Pair<Color, String>>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        colors.forEach { color ->
            ColorContent(
                displayLabel = color.second,
                color = color.first
            )
        }
        if (colors.size < COLOR_CHUNK_SIZE) {
            repeat(COLOR_CHUNK_SIZE - colors.size) {
                ColorContent(
                    displayLabel = "",
                    color = SDGColor.Transparent
                )
            }
        }
    }
}

@Composable
private fun ColorContent(
    displayLabel: String,
    color: Color,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier
                .size(40.dp)
                .then(
                    if (color == SDGColor.Neutral0 || color == SDGColor.Neutral0_a10) {
                        Modifier.border(
                            width = 1.dp,
                            color = SDGColor.Neutral200,
                            shape = CircleShape
                        )
                    } else {
                        Modifier
                    }
                )
                .background(
                    color = color,
                    shape = CircleShape
                )
        )
        SDGText(
            text = displayLabel,
            textColor = SDGColor.Neutral400,
            typography = SDGTypography.Body3R
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewColorsContentWithTitle() {
    ShoplDesignGuideTheme {
        ColorsContent(
            title = "Color Description",
            colors = persistentListOf(
                SDGColor.Neutral700 to "700",
                SDGColor.Neutral500 to "500",
                SDGColor.Neutral300 to "300",
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewColorsContent() {
    ShoplDesignGuideTheme {
        ColorsContent(
            colors = persistentListOf(
                SDGColor.Neutral700 to "700",
                SDGColor.Neutral500 to "500",
                SDGColor.Neutral300 to "300",
            )
        )
    }
}
