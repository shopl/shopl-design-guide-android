package com.shopl.sdg.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface

data class SDGBoxFixedTabItem(
    val title: String,
    val subTitle: String? = null
)

sealed class SDGBoxFixedTabType {
    @Stable
    object Solid : SDGBoxFixedTabType()

    @Stable
    data class Line(val lineColor: Color) : SDGBoxFixedTabType()
}

@Composable
fun SDGBoxFixedTab(
    type: SDGBoxFixedTabType,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    tabItems: List<SDGBoxFixedTabItem>,
    selectedTabIndex: Int,
    backgroundColor: Color = SDGColor.Neutral0,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .then(
                if (type is SDGBoxFixedTabType.Line) {
                    Modifier.border(
                        width = 1.dp,
                        color = type.lineColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            )
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = backgroundColor)
            .padding(
                vertical = 2.dp,
                horizontal = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tabItems.forEachIndexed { index, item ->
            if (index != 0) {
                VerticalDivider(
                    modifier = Modifier
                        .height(16.dp),
                    color = SDGColor.Neutral200
                )
            }
            Tab(
                item = item,
                onClick = {
                    onClick(index)
                },
                selected = (index == selectedTabIndex),
            )
        }
    }
}

@Composable
private fun RowScope.Tab(
    item: SDGBoxFixedTabItem,
    onClick: () -> Unit,
    selected: Boolean,
) {
    Column(
        modifier = Modifier
            .weight(1F)
            .padding(vertical = 12.dp)
            .clickable(hasRipple = false) {
                onClick()
            },
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        IOText(
            modifier = Modifier.fillMaxWidth(),
            text = item.title,
            textColor = if (selected) SDGColor.Neutral700 else SDGColor.Neutral350,
            fontSize = 14.sp,
            typeface = if (selected) IOTypeface.SEMI_BOLD else IOTypeface.REGULAR,
            textAlign = TextAlign.Center
        )
        item.subTitle?.let {
            IOText(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                textColor = if (selected) SDGColor.Neutral500 else SDGColor.Neutral350,
                fontSize = 12.sp,
                typeface = IOTypeface.REGULAR,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
@Preview
private fun PreviewTwoOptions() {
    SDGBoxFixedTab(
        type = SDGBoxFixedTabType.Solid,
        tabItems = listOf(SDGBoxFixedTabItem("Label"), SDGBoxFixedTabItem("Label")),
        selectedTabIndex = 0,
    ) {}
}

@Composable
@Preview
private fun PreviewThreeOptions() {
    SDGBoxFixedTab(
        type = SDGBoxFixedTabType.Line(SDGColor.Neutral200),
        tabItems = listOf(
            SDGBoxFixedTabItem("Label", "SubLabel"),
            SDGBoxFixedTabItem("Label", "SubLabel"),
            SDGBoxFixedTabItem("Label", "SubLabel")
        ),
        selectedTabIndex = 1,
    ) {}
}