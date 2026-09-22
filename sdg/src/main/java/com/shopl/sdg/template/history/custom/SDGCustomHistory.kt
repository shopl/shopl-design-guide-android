package com.shopl.sdg.template.history.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4

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
fun SDGCustomHistory(
    position: SDGCustomHistoryPosition,
    dotColor: Color,
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = spacedBy(Spacing16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HistoryTimeLine(
            position = position,
            dotColor = dotColor
        )

        Column {
            header()
            body()
        }
    }
}

@Composable
private fun HistoryTimeLine(
    position: SDGCustomHistoryPosition,
    dotColor: Color,
    modifier: Modifier = Modifier,
) {
    val showTopLine = when (position) {
        SDGCustomHistoryPosition.FIRST,
        SDGCustomHistoryPosition.TOP,
            -> false

        SDGCustomHistoryPosition.MIDDLE,
        SDGCustomHistoryPosition.LAST,
            -> true
    }

    val showBottomLine = when (position) {
        SDGCustomHistoryPosition.FIRST,
        SDGCustomHistoryPosition.LAST,
            -> false

        SDGCustomHistoryPosition.TOP,
        SDGCustomHistoryPosition.MIDDLE,
            -> true
    }

    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(Spacing4),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showTopLine) {
            Line(
                modifier = Modifier.height(TopLineHeight),
            )
        } else {
            Spacer(
                modifier = Modifier.height(TopLineHeight),
            )
        }

        Dot(
            color = dotColor,
        )

        if (showBottomLine) {
            Line(
                modifier = Modifier.weight(1f),
            )
        } else {
            Spacer(
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun Line(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(1.dp)
            .background(SDGColor.Neutral200),
    )
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
            ),
    )
}

private val DotSize = 8.dp
private val TopLineHeight = 21.dp