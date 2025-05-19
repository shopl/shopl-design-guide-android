package com.shopl.sdg_common.util

import androidx.compose.runtime.getValue
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.shopl.sdg_common.foundation.SDGColor

@Composable
fun ShimmerAnimation(
    modifier:Modifier
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = SDGColor.ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(
        brush = brush,
        modifier = modifier,
    )
}

@Composable
fun ShimmerItem(
    brush: Brush,
    modifier: Modifier
) {
    Spacer(
        modifier = modifier
            .background(brush = brush)
    )
}