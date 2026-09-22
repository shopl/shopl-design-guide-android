package com.shopl.sdg.template.history.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16

/**
 * SDG - History - CustomHistory
 *
 * Dot과 Line 타임라인을 기반으로 다양한 이력과 진행 흐름을 커스텀 구성하는 템플릿
 *
 * @version 2.3.46
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22986-3555&m=dev">Figma</a>
 */
@Composable
fun SDGCustomHistory() {
    Row(
        horizontalArrangement = spacedBy(Spacing16),
        verticalAlignment = Alignment.CenterVertically
    ) {

    }
}

@Composable
private fun HistoryTimeLine(modifier: Modifier = Modifier) {

}

@Composable
private fun Line(modifier: Modifier = Modifier) {

}

@Composable
private fun Dot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(DotSize)
            .background(
                color = color,
                shape = CircleShape,
            )
    )
}

private val DotSize = 8.dp
