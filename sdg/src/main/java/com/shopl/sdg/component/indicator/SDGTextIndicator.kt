package com.shopl.sdg.component.indicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

private val CIRCLE_SIZE = 18.dp
private val DIVIDER_WIDTH = 12.dp

/**
 * SDG - Indicator - Text Indicator
 *
 * 지난 화면의 completed와 현재 위치를 text로 표현하여 화면의 타이틀 역할도 하는 컴포넌트
 *
 * @param steps Step을 구성하는 텍스트 리스트, 각 Step은 라벨만 가지고 있고 상태값은 포함하지 않음
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6870-15639&m=dev">Figma</a>
 */
@Composable
fun SDGTextIndicator(
    currentStep: Int,
    steps: PersistentList<String>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        val maxTextWidth = rememberMaxTextWidthForIndicator(maxWidth, steps.size)
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider()
            steps.forEachIndexed { index, label ->
                val isCurrent = index == currentStep
                val isCompleted = index < currentStep
                val circleColor by animateColorAsState(
                    targetValue = when {
                        isCurrent || isCompleted -> SDGColor.Neutral700
                        else -> SDGColor.Neutral100
                    }
                )
                val circleSize by animateDpAsState(
                    targetValue = if (!isCurrent) CIRCLE_SIZE else 0.dp,
                )

                Box(
                    modifier = Modifier
                        .widthIn(min = CIRCLE_SIZE),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = isCurrent,
                        enter = expandHorizontally(expandFrom = Alignment.CenterHorizontally) + fadeIn(),
                        exit = shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally) + fadeOut()
                    ) {
                        TextIndicatorUnit(
                            modifier = Modifier.widthIn(max = maxTextWidth),
                            label = label
                        )
                    }

                    androidx.compose.animation.AnimatedVisibility(
                        visible = !isCurrent,
                        enter = scaleIn(transformOrigin = TransformOrigin.Center) + fadeIn(),
                        exit = scaleOut(transformOrigin = TransformOrigin.Center) + fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(circleSize)
                                .background(
                                    color = SDGColor.Neutral200,
                                    shape = CircleShape
                                )
                                .padding(4.dp)
                                .background(circleColor, CircleShape)
                        )
                    }
                }
                Divider()
            }
            Divider(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun TextIndicatorUnit(
    modifier: Modifier = Modifier,
    label: String,
) {
    Box(
        modifier = modifier
            .height(18.dp)
            .background(
                color = SDGColor.Neutral700,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = 8.dp
            )
            .animateContentSize()
    ) {
        SDGText(
            modifier = Modifier.align(Alignment.Center),
            text = label,
            typography = SDGTypography.Body3R,
            textColor = SDGColor.Neutral0,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Divider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(1.dp)
            .width(DIVIDER_WIDTH)
            .background(
                color = SDGColor.Neutral200
            )
    )
}

@Composable
private fun rememberMaxTextWidthForIndicator(
    maxWidth: Dp,
    stepsSize: Int,
): Dp {
    return remember(stepsSize, maxWidth) {
        val totalFixed = (CIRCLE_SIZE * (stepsSize - 1)) + (DIVIDER_WIDTH * (stepsSize + 1))
        (maxWidth - totalFixed).coerceAtLeast(0.dp)
    }
}

@Preview
@Composable
private fun PreviewSDGTextIndicator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral100
            )
            .padding(
                vertical = 20.dp
            )
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SDGTextIndicator(
            currentStep = 0,
            steps = persistentListOf("Step 1", "Step 2", "Step 3", "Step 4", "Step 5")
        )
        SDGTextIndicator(
            currentStep = 1,
            steps = persistentListOf("Step 1", "Step 2", "Step 3", "Step 4", "Step 5")
        )
        SDGTextIndicator(
            currentStep = 2,
            steps = persistentListOf("Step 1", "Step 2", "Step 3", "Step 4", "Step 5")
        )
        SDGTextIndicator(
            currentStep = 3,
            steps = persistentListOf("Step 1", "Step 2", "Step 3", "Step 4", "Step 5")
        )
        SDGTextIndicator(
            currentStep = 4,
            steps = persistentListOf("Step 1", "Step 2", "Step 3", "Step 4", "Step 5")
        )

        HorizontalDivider(
            color = SDGColor.Neutral700
        )

        /**
         * 화면 크기 최대 Case
         */
        SDGTextIndicator(
            currentStep = 3,
            steps = persistentListOf("Step 1", "Step 2", "Step 3", "SDGTextIndicatorSDGTextIndicatorSDGTextIndicatorStep 4", "Step 5")
        )
        SDGTextIndicator(
            modifier = Modifier.padding(horizontal = 20.dp),
            currentStep = 3,
            steps = persistentListOf("Step 1", "Step 2", "Step 3", "SDGTextIndicatorSDGTextIndicatorSDGTextIndicatorStep 4", "Step 5")
        )
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            SDGTextIndicator(
                modifier = Modifier.padding(horizontal = 20.dp),
                currentStep = 3,
                steps = persistentListOf("Step 1", "Step 2", "Step 3", "SDGTextIndicatorSDGTextIndicatorSDGTextIndicatorStep 4", "Step 5")
            )
        }

    }
}

@Preview
@Composable
private fun PreviewSDGTextIndicatorAnimated() {
    val steps = persistentListOf("Step 1", "Step 2", "Step 3", "SDGTextIndicatorSDGTextIndicatorSDGTextIndicatorStep 4", "Step 5")
    var currentStep by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral100
            )
            .padding(
                vertical = 20.dp
            )
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SDGTextIndicator(
            currentStep = currentStep,
            steps = steps
        )
        SDGText(
            modifier = Modifier.clickable {
                currentStep = (currentStep + 1) % steps.size
            },
            text = "Click Move Next",
            typography = SDGTypography.Body3R,
            textColor = SDGColor.Neutral700,
        )
    }
}