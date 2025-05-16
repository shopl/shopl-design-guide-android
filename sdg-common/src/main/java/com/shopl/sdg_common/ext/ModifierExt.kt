package com.shopl.sdg_common.ext

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.clickable(
    hasRipple: Boolean = true,
    rippleColor: Color = SDGColor.Primary300,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    debounceTimeMills: Long = 500,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val scope = rememberCoroutineScope()
    val isClickable = remember { mutableStateOf(true) }
    val handleClick: () -> Unit = {
        if (isClickable.value) {
            onClick()
            isClickable.value = false
            scope.launch {
                delay(debounceTimeMills)
                isClickable.value = true
            }
        }
    }

    if (hasRipple) {
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { handleClick() },
            role = role,
            indication = ripple(
                color = rippleColor
            ),
            interactionSource = remember { MutableInteractionSource() }
        )
    } else {
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { handleClick() },
            role = role,
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}

enum class ButtonState { Pressed, Idle }

fun Modifier.rippleClickable(
    rippleColor: Color = SDGColor.Neutral350,
    rippleRadius: Dp,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    debounceTimeMills: Long = 500,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val scope = rememberCoroutineScope()
    val isClickable = remember { mutableStateOf(true) }
    val handleClick: () -> Unit = {
        if (isClickable.value) {
            onClick()
            isClickable.value = false
            scope.launch {
                delay(debounceTimeMills)
                isClickable.value = true
            }
        }
    }

    Modifier
        .clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { handleClick() },
            role = role,
            indication = ripple(
                color = rippleColor,
                radius = rippleRadius,
            ),
            interactionSource = remember { MutableInteractionSource() }
        )
}

fun Modifier.bounceClickable(
    targetScale: Float = 0.95F,
    debounceTimeMills: Long = 500,
    onClick: () -> Unit
) = composed {

    val scope = rememberCoroutineScope()
    val isClickable = remember { mutableStateOf(true) }
    val handleClick: () -> Unit = {
        if (isClickable.value) {
            onClick()
            isClickable.value = false
            scope.launch {
                delay(debounceTimeMills)
                isClickable.value = true
            }
        }
    }

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) targetScale else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { handleClick() },
                onPress = {
                    buttonState = ButtonState.Pressed
                    tryAwaitRelease()
                    buttonState = ButtonState.Idle
                }
            )
        }
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.shimmerBackground(
    shape: Shape = RectangleShape,
    color: Color = SDGColor.Neutral200,
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 3000, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
        label = "",
    )
    val shimmerColors = listOf(
        color.copy(alpha = 0.95f),
        color.copy(alpha = 0.4f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 100f, translateAnimation + 100f),
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush, shape))
}

fun Modifier.shadow(shape: Shape = CircleShape, elevation: Dp = 7.dp) = composed {
    this.shadow(
        elevation = elevation,
        shape = shape,
        ambientColor = SDGColor.Neutral200,
        spotColor = SDGColor.Neutral400
    )
}

fun Modifier.shadowBehind(
    color: Color = SDGColor.Neutral600_a10,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 3.dp,
    spread: Dp = 3.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = -spreadPixel,
                top = -spreadPixel,
                right = this.size.width + spreadPixel,
                bottom = this.size.height + spreadPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

/**
 * Drop Shadow 적용
 * drawBehind 블록을 활용하여 배경 뒤에 그림자를 그립니다.
 *
 * @param shape 그림자의 Shape
 * @param color 그림자 색상
 * @param blur 그림자에 적용할 Blur 마스크 반경
 * @param offsetY 그림자의 세로 오프셋
 * @param offsetX 그림자의 가로 오프셋
 * @param spread  그림자 영역에 추가로 확장할 크기
 */
fun Modifier.dropShadow(
    shape: Shape,
    color: Color,
    blur: Dp,
    offsetY: Dp,
    offsetX: Dp,
    spread: Dp = 0.dp
) = this.drawBehind {
    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint().apply {
        this.color = color
    }

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}
