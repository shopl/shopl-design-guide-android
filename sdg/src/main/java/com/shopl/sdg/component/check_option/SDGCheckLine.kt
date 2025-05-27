package com.shopl.sdg.component.check_option

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

@Composable
fun SDGCheckLine(
    unCheckedStrokeColor: Color = SDGColor.Neutral300,
    checkedStrokeColor: Color = SDGColor.Red400,
    unCheckedColor: Color = SDGColor.Neutral300,
    checkedColor: Color = SDGColor.Red400,
    unCheckedBackgroundColor: Color = Color.Transparent,
    checkedBackgroundColor: Color = SDGColor.Primary300,
    isChecked: Boolean = false,
    clickPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null
) {

    val strokeColor by animateColorAsState(
        targetValue = if (isChecked)
            checkedStrokeColor
        else
            unCheckedStrokeColor,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    val checkColor by animateColorAsState(
        targetValue = if (isChecked)
            checkedColor
        else
            unCheckedColor,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked)
            checkedBackgroundColor
        else
            unCheckedBackgroundColor,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .padding(clickPadding)
            .border(
                width = 1.dp,
                color = strokeColor,
                shape = CircleShape
            )
            .clip(
                shape = CircleShape
            )
            .background(
                color = backgroundColor,
            )
            .clickable(
                hasRipple = false,
                onClick = {
                    onClick?.invoke()
                }
            )
    ) {
        Image(
            modifier = Modifier
                .padding(1.dp)
                .wrapContentSize(align = Alignment.Center),
            painter = painterResource(
                id = R.drawable.ic_common_check_s,
            ),
            colorFilter = ColorFilter.tint(color = checkColor),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
private fun SDGCheckLinePreView() {
    var test by remember {
        mutableStateOf(false)
    }
    SDGCheckLine(clickPadding = PaddingValues(20.dp), isChecked = test) {
        test = !test
    }
}