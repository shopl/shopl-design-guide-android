package com.shopl.sdg.component.tab.box

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Tab - Box Tab
 *
 * 라벨과 세부 선택 값을 조합하여 화면 조건을 유연하게 전환하는 박스형 탭 컴포넌트
 *
 * @version 2.3.47
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=20902-19365&m=dev">Figma</a>
 */
@Composable
fun SDGBoxTab() {

}

@Composable
private fun BoxTabItem(
    item: SDGBoxTabItem,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = spacedBy(Spacing2, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        SDGText(
            text = item.label,
            typography = item.state.labelTypography,
            textColor = item.state.labelTextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing2)
        )
        val twoDepth = item.showTwoDepth
        if (twoDepth is SDGBoxTabItem.ShowTwoDepth.True) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SDGText(
                    text = twoDepth.text,
                    typography = SDGTypography.Body3R,
                    textColor = item.state.twoDepthTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )

                twoDepth.count?.let { count ->
                    SDGText(
                        text = "+${count}",
                        typography = SDGTypography.Body3SB,
                        textColor = item.state.twoDepthTextColor,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBoxTabItem() {
    Row {
        BoxTabItem(
            item = SDGBoxTabItem(
                state = SDGBoxTabItemState.Selected,
                label = "Label",
                showTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("Selected Text", "2")
            ),
            modifier = Modifier.width(89.dp)
        )
        BoxTabItem(
            item = SDGBoxTabItem(
                state = SDGBoxTabItemState.Unselected,
                label = "Label",
                showTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("Selected Text", "2")
            ),
            modifier = Modifier.width(89.dp)
        )
    }
}