package com.shopl.sdg.component.toggle

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor

@Composable
fun SDGToggle(
    isChecked: Boolean,
    onCheckedChange: (() -> Unit)?,
    checkedTrackColor: Color = SDGColor.Primary300,
    uncheckedTrackColor: Color = SDGColor.Neutral300,
    thumbColor: Color = Color.White,
    gapBetweenThumbAndTrackEdge: Dp = 2.dp,
    clickPadding: PaddingValues = PaddingValues(),
) {
    val width = 40.dp
    val height = 22.dp
    val scale = 1f
    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    val density = LocalDensity.current
    val targetPosition = remember(isChecked, density) {
        with(density) {
            if (isChecked)
                (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx()
            else
                (thumbRadius + gapBetweenThumbAndTrackEdge).toPx()
        }
    }
    val animatePosition by animateFloatAsState(targetValue = targetPosition)

    Canvas(
        modifier = Modifier
            .padding(clickPadding)
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onCheckedChange?.invoke()
                    }
                )
            }
    ) {
        drawRoundRect(
            color = if (isChecked) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 11.dp.toPx(), y = 11.dp.toPx())
        )

        // Thumb
        drawCircle(
            color = thumbColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition,
                y = size.height / 2
            )
        )
    }
}

@Preview
@Composable
private fun SDGTogglePreview() {

    var test by remember {
        mutableStateOf(false)
    }

    SDGToggle(
        isChecked = test,
        gapBetweenThumbAndTrackEdge = 1.dp,
        onCheckedChange = { test = !test }
    )
}