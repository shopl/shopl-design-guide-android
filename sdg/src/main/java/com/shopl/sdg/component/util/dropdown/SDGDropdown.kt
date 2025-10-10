package com.shopl.sdg.component.util.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

/**
 * [RowScope] [SDGDropdown]
 */
@Composable
fun RowScope.SDGDropdown(
    weight: Float,
    text: String? = null,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    Box(Modifier.weight(weight)) {
        com.shopl.sdg.component.dropdown.SDGDropdown(
            text = text,
            backgroundColor = backgroundColor,
            marginValues = marginValues,
            onClick = onClick
        )
    }
}

@Preview
@Composable
private fun PreviewSDGBasicDropdown() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing20),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20)
    ) {
        Row {
            SDGDropdown(
                weight = 1F,
                text = "Weight 1F",
                backgroundColor = SDGColor.Neutral50,
            )
            SDGDropdown(
                weight = 1F,
                text = "Weight 1F",
                backgroundColor = SDGColor.Neutral50,
            )
        }
    }
}