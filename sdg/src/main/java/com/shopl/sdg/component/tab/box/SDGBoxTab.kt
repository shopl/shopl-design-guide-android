package com.shopl.sdg.component.tab.box

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shopl.sdg_common.foundation.SDGColor
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
        verticalArrangement = spacedBy(Spacing2),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        SDGText(
            text = item.label,
            typography = SDGTypography.Body2SB,
            textColor = SDGColor.Neutral700,
            modifier = Modifier.padding(vertical = Spacing2)
        )
        val twoDepth = item.showTwoDepth
        if (twoDepth is SDGBoxTabItem.ShowTwoDepth.True) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SDGText(
                    text = twoDepth.text,
                    typography = SDGTypography.Body3R,
                    textColor = SDGColor.Neutral500
                )

                twoDepth.count?.let { count ->
                    SDGText(
                        text = "+${count}",
                        typography = SDGTypography.Body3SB,
                        textColor = SDGColor.Neutral500
                    )
                }
            }
        }
    }
}