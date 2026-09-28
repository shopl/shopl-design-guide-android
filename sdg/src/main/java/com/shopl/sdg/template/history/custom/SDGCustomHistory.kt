package com.shopl.sdg.template.history.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.history.custom.preview.SDGCustomHistoryPreviewParameterProvider
import com.shopl.sdg.template.history.custom.preview.SDGCustomHistoryPreviewParams
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - History - Custom History
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
    body: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = spacedBy(Spacing16),
        ) {
            HistoryTimeLine(
                modifier = Modifier
                    .width(TimelineWidth)
                    .fillMaxHeight(),
                showTopLine = position.showTopLine,
                showBottomLine = position.showBottomLine,
                dotColor = dotColor,
            )

            HistoryHeader(
                header = header,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = Spacing20, bottom = Spacing16),
            )
        }

        body?.let {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = spacedBy(Spacing16),
            ) {
                Box(
                    modifier = Modifier
                        .width(TimelineWidth)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    TimelineLine(
                        visible = position.showBottomLine,
                        modifier = Modifier.fillMaxHeight(),
                    )
                }

                HistoryBody(
                    body = it,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = Spacing20),
                )
            }
        }
    }
}

@Composable
private fun HistoryTimeLine(
    showTopLine: Boolean,
    showBottomLine: Boolean,
    dotColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimelineLine(
            visible = showTopLine,
            modifier = Modifier.height(TopLineExtraHeight),
        )
        TimelineLine(
            visible = showTopLine,
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.height(Spacing4))
        Box(
            modifier = Modifier
                .size(DotSize)
                .background(dotColor, CircleShape),
        )
        Spacer(modifier = Modifier.height(Spacing4))
        TimelineLine(
            visible = showBottomLine,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun TimelineLine(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(LineWidth)
            .then(if (visible) Modifier.background(SDGColor.Neutral200) else Modifier),
    )
}

@Composable
private fun HistoryHeader(
    header: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        header()
    }
}

@Composable
private fun HistoryBody(
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        body()
    }
}

private val DotSize = 8.dp
private val LineWidth = 1.dp
private val TimelineWidth = 16.dp
private val TopLineExtraHeight = 4.dp

@Preview(showBackground = true)
@Composable
private fun PreviewSDGCustomHistory(
    @PreviewParameter(SDGCustomHistoryPreviewParameterProvider::class)
    params: SDGCustomHistoryPreviewParams,
) {
    SDGCustomHistory(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        position = params.position,
        dotColor = SDGColor.Primary300,
        header = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(params.headerModifier)
                    .padding(horizontal = 12.dp),
            ) {
                SDGText(
                    text = params.header,
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral700,
                )
            }
        },
        body = params.body?.let { bodyText ->
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SDGColor.Neutral200, RoundedCornerShape(8.dp))
                        .padding(params.bodyPadding),
                ) {
                    SDGText(
                        text = bodyText,
                        typography = SDGTypography.Body2R,
                        textColor = SDGColor.Neutral700,
                    )
                }
            }
        },
    )
}
